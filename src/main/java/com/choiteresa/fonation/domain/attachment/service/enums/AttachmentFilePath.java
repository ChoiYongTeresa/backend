package com.choiteresa.fonation.domain.attachment.service.enums;

public enum AttachmentFilePath {
    IMAGE_FILE_PATH("src/main/resources/static/img/"),
    DOCUMENT_FILE_PATH("src/main/resources/static/document/");
    private final String filepath;
    public String getPath(){
        return this.filepath;
    }
    public String makeFilePath(String filename){return this.filepath+filename+".jpg";}
    AttachmentFilePath(String path){
        this.filepath=path;
    }
}
