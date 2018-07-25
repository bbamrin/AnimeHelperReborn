package bbamrin.animehelperreborn.Model.internalModel;

public class Anime {

    private String name;
    private boolean isChecked;
    private String imageAddress;
    private String status;
    private String numberInShikimori;

    public Anime(String name, boolean isChecked, String imageAddress, String status, String numberInShikimori) {
        this.name = name;
        this.isChecked = isChecked;
        this.imageAddress = imageAddress;
        this.status = status;
        this.numberInShikimori = numberInShikimori;
    }

    public String getNumberInShikimori() {

        return numberInShikimori;
    }

    public void setNumberInShikimori(String numberInShikimori) {
        this.numberInShikimori = numberInShikimori;
    }

    public Anime(String name, boolean isChecked, String imageAddress, String status) {

        this.name = name;
        this.isChecked = isChecked;
        this.imageAddress = imageAddress;
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    public String getImageAddress() {
        return imageAddress;
    }

    public void setImageAddress(String imageAddress) {
        this.imageAddress = imageAddress;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}