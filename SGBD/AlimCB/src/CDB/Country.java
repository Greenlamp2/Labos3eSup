/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package CDB;


public class Country {
    private String code;
    private String name;
    private String url;

    public Country(){
        code = null;
        name = null;
        url = null;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
