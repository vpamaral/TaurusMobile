package br.com.prodap.taurusmobile.OpenFile;

public interface OnCloseListener {
    /***
     * This methods will be called when the user touches the cancel button.
     */
    void onCancel();

    /***
     * This method will be called when the user selects a file/folder and touches the OK button.
     * @param selectedFile The file or folder that the user has selected.
     */
    void onOk(String selectedFile);
}
