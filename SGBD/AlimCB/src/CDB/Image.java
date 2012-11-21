/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package CDB;

public class Image {
    private String type;
    private String size;
    private int height;
    private int width;
    private String url;
    private String id;

    public Image(){
        type = null;
        size = null;
        height = -1;
        width = -1;
        url = null;
        id = null;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
