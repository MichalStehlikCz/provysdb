package com.provys.provysdb.sqlparser;

import java.util.Collection;
import java.util.Scanner;

public interface SqlTokenizer {
    Collection<SqlParsedToken> tokenize(String source);

    Collection<SqlParsedToken> tokenize(Scanner scanner);
}
