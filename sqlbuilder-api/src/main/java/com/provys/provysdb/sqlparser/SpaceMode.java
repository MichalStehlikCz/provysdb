package com.provys.provysdb.sqlparser;

public enum SpaceMode {
    FORCE, // space is required on this position
    NORMAL, // space is used unless next to NONE mode token
    NONE // space is not required unless forced
}
