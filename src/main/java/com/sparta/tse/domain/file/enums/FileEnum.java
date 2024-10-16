package com.sparta.tse.domain.file.enums;

public enum FileEnum {
    CARD(FolderName.CARD),
    BOARD(FolderName.BOARD);

    private final String folderName;

    FileEnum(String folderName) {
        this.folderName = folderName;
    }

    public static class FolderName {
        public static final String CARD = "CARD";
        public static final String BOARD = "BOARD";
    }
}
