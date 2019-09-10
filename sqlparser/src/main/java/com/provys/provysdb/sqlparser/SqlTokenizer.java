package com.provys.provysdb.sqlparser;

/**
 * Represents single token parsed from SQL source. Gives access to type of token and its position in original file,
 * derived classes add additional information (like parsed text)
 */
public abstract class SqlTokenizer {
    CREATE OR REPLACE TYPE BODY KER_GenTokenizer_TO
    IS

    STATIC FUNCTION mfw_GetVersion
    RETURN VARCHAR2
    IS
/*
15.04.2019  1.0  stehlik          [MS190415-0232756] Uvodni impleemntace package header parseru
*/
            BEGIN
    RETURN '1.0.0.0.15-04-2019';
    END;

    STATIC FUNCTION mf_Create(
            p_MaxTokens INTEGER :=1000000
    ) RETURN KER_GenTokenizer_TO
    IS
    lo_GenTokenizer KER_GenTokenizer_TO;
    BEGIN
    lo_GenTokenizer:=KER_GenTokenizer_TO(MaxTokens => p_MaxTokens);
    RETURN lo_GenTokenizer;
    END;

    MEMBER FUNCTION mf_GetMaxTokens(
            SELF KER_GenTokenizer_TO
    ) RETURN INTEGER
    IS
            BEGIN
    RETURN SELF.MaxTokens;
    END;

    MEMBER PROCEDURE mp_Tokenize(
            SELF KER_GenTokenizer_TO
            , pt_SrcLine KER_NoteList_TT
            , ot_Token OUT NOCOPY KER_GenToken_TT
    )
    IS
    l_Line VARCHAR2(4000);
    l_Token KER_GenToken_TO;
    l_Line_i SIMPLE_INTEGER:=1;

    PROCEDURE mpi_ReadSingleLineComment
    IS
            BEGIN
    l_Token:=KER_GenToken_TO.mf_Create('COMMENT', '--', l_Line);
    l_Line:=NULL;
    END;

    PROCEDURE mpi_ReadMultiLineComment
    IS
    l_Comment VARCHAR2(32000);
    BEGIN
    WHILE (INSTR(l_Line, '*/')=0) LOOP
    l_Comment:=l_Comment||l_Line;
    l_Line_i:=l_Line_i+1;
    IF (l_Line_i>pt_SrcLine.COUNT) THEN
        KER_Exception_EP.mp_Raise('Unfinished comment');
    END IF;
    l_Line:=' '||LTRIM(RTRIM(pt_SrcLine(l_Line_i), ' '||CHR(9)||CHR(10)||CHR(13)), ' '||CHR(9));
    END LOOP;
    l_Comment:=l_Comment||SUBSTR(l_Line, 1, INSTR(l_Line, '*/')+1);
    l_Token:=KER_GenToken_TO.mf_Create('COMMENT', '/*', l_Comment);
    l_Line:=SUBSTR(l_Line, INSTR(l_Line, '*/')+2);
    END;

    PROCEDURE mpi_ReadStringLiteral
    IS
    l_Pos PLS_INTEGER :=2;
    BEGIN
            LOOP
    l_Pos:=INSTR(l_Line, '''', l_Pos);
    IF (l_Pos=0) THEN
        KER_Exception_EP.mp_Raise('End of string not found on line '||l_Line_i);
    END IF;
    IF (SUBSTR(l_Line, l_Pos+1, 1)='''') THEN
    /* double apostrophe is escape for apos character */
    l_Pos:=l_Pos+2;
    ELSE
            EXIT;
    END IF;
    END LOOP;
    l_Token:=KER_GenToken_TO.mf_Create('LITERAL', 'VARCHAR2', SUBSTR(l_Line, 1, l_Pos));
    l_Line:=SUBSTR(l_Line, l_Pos+1);
    END;

    PROCEDURE mpi_ReadDateLiteral
    IS
    l_Pos PLS_INTEGER;
    BEGIN
    l_Pos:=INSTR(l_Line, '''', 6);
    IF (l_Pos=0) THEN
      KER_Exception_EP.mp_Raise('Unfinished date literal on line '||l_Line_i);
    END IF;
    l_Token:=KER_GenToken_TO.mf_Create('LITERAL', 'DATE', SUBSTR(l_Line, 1, l_Pos));
    l_Line:=SUBSTR(l_Line, l_Pos+1);
    END;

    PROCEDURE mpi_ReadNumericLiteral
    IS
    l_Pos PLS_INTEGER :=1;
    l_Dot BOOLEAN :=FALSE; /* indicates we already found . */
    BEGIN
    WHILE (SUBSTR(l_Line, l_Pos, 1) BETWEEN '0' AND '9') OR (SUBSTR(l_Line, l_Pos, 1)='.') LOOP
    IF (SUBSTR(l_Line, l_Pos, 1)='.') THEN
    IF l_Dot THEN
          KER_Exception_EP.mp_Raise('Cannot have . in number literal twice');
    END IF;
    l_Dot:=TRUE;
    END IF;
    l_Pos:=l_Pos+1;
    END LOOP;
    l_Token:=KER_GenToken_TO.mf_Create('LITERAL', 'NUMBER', SUBSTR(l_Line, 1, l_Pos-1));
    l_Line:=SUBSTR(l_Line, l_Pos);
    END;

    PROCEDURE mpi_ReadName
    IS
    l_Pos PLS_INTEGER :=1;
    BEGIN
    WHILE (SUBSTR(l_Line, l_Pos, 1) BETWEEN 'A' AND 'Z')
    OR  (SUBSTR(l_Line, l_Pos, 1) BETWEEN 'a' AND 'z')
    OR  (SUBSTR(l_Line, l_Pos, 1) BETWEEN '0' AND '9')
    OR  (SUBSTR(l_Line, l_Pos, 1) IN ('_', '$', '.', '%', '#'))
    LOOP
    l_Pos:=l_Pos+1;
    END LOOP;
    l_Token:=KER_GenToken_TO.mf_Create('NAME', SUBSTR(l_Line, 1, l_Pos-1));
    l_Line:=SUBSTR(l_Line, l_Pos);
    END;

    PROCEDURE mpi_ReadAssignment
    IS
            BEGIN
    l_Token:=KER_GenToken_TO.mf_Create('SYMBOL', ':=', ':=');
    l_Line:=SUBSTR(l_Line, 3);
    END;

    PROCEDURE mpi_ReadParamRef
    IS
            BEGIN
    l_Token:=KER_GenToken_TO.mf_Create('SYMBOL', '=>', '=>');
    l_Line:=SUBSTR(l_Line, 3);
    END;

    PROCEDURE mpi_ReadSymbol
    IS
            BEGIN
    l_Token:=KER_GenToken_TO.mf_Create('SYMBOL', SUBSTR(l_Line, 1, 1), SUBSTR(l_Line, 1, 1));
    l_Line:=SUBSTR(l_Line, 2);
    END;
    BEGIN
    ot_Token:=KER_GenToken_TT();
    WHILE (l_Line_i<=pt_SrcLine.COUNT) LOOP
    /* read line, right trim it (white space on start / end of line is ignored) */
    l_Line:=RTRIM(pt_SrcLine(l_Line_i), ' '||CHR(9)||CHR(10)||CHR(13));
    LOOP
    /* left trim after each step */
    l_Line:=LTRIM(l_Line, ' '||CHR(9));
    EXIT WHEN (l_Line IS NULL);
    IF (l_Line LIKE '--%') THEN
    /* single line comment */
    mpi_ReadSingleLineComment;
    ELSIF (l_Line LIKE '/*%') THEN
    /* Multi-line comment */
    mpi_ReadMultiLineComment;
    ELSIF (l_Line LIKE '''%') THEN
    /* String literal (without special escaping) */
    mpi_ReadStringLiteral;
    ELSIF (UPPER(l_Line) LIKE 'Q''%') THEN
    /* String literal with escaping */
        KER_Exception_EP.mp_Raise('Escaped strings not supported at the moment');
    ELSIF (UPPER(l_Line) LIKE 'DATE''%') THEN
            /* Date literal */
            mpi_ReadDateLiteral;
    ELSIF (SUBSTR(l_Line, 1, 1) BETWEEN '0' AND '9') OR (SUBSTR(l_Line, 1, 1)='.') THEN
            /* numeric literal; at the moment, we only support simple literals without exponent */
            mpi_ReadNumericLiteral;
    ELSIF (SUBSTR(l_Line, 1, 1) BETWEEN 'A' AND 'Z') OR (SUBSTR(l_Line, 1, 1) BETWEEN 'a' AND 'z') THEN
            /* name */
            mpi_ReadName;
    ELSIF (l_Line LIKE ':=%') THEN
    /* default value assignment */
    mpi_ReadAssignment;
    ELSIF (l_Line LIKE '=>%') THEN
    /* named parameter */
    mpi_ReadParamRef;
    ELSIF (SUBSTR(l_Line, 1, 1) IN ('(', ')', ',', '+', '-', '/', '*', ';', '.', '%', '=', '>', '<')) THEN
            /* characters acting as token on their own */
            mpi_ReadSymbol;
    ELSE
        KER_Exception_EP.mp_Raise('Unrecognised token on line '||l_Line_i||': "'||l_Line||'"');
    END IF;
    ot_Token.EXTEND;
    ot_Token(ot_Token.COUNT):=l_Token;
    IF (ot_Token.COUNT>SELF.MaxTokens) THEN
        KER_Exception_EP.mp_Raise('Cannot parse package this big');
    END IF;
    END LOOP;
    l_Line_i:=l_Line_i+1;
    END LOOP;
    END;

    END;

}
