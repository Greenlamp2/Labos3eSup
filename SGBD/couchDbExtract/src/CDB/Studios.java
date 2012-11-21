/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package CDB;


public class Studios {
    private String url;
    private String name;
    private int id;

    public Studios(){
        url = null;
        name = null;
        id = -1;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
