
package com.yiyuan.player.engine;

public class FileAlreadyExistException extends DownloadException {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    public FileAlreadyExistException(String message) {

        super(message);
    }

}
