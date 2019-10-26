package com.provys.provysdb.sqlparser;

import com.provys.provysdb.sqlbuilder.CodeBuilder;

import java.util.List;
import java.util.Scanner;

public interface SqlTokenizer {
    List<SqlParsedToken> tokenize(String source);

    List<SqlParsedToken> tokenize(Scanner scanner);

    CodeBuilder getBinds(String source);

    CodeBuilder getBinds(Scanner scanner);
}
