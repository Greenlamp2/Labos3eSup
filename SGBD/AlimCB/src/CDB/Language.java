/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package CDB;


public class Language {
    private String code;
    private String name;
    private String native_name;

    public Language(){
        code = null;
        name = null;
        native_name = null;
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

    public String getNative_name() {
        return native_name;
    }

    public void setNative_name(String native_name) {
        this.native_name = native_name;
    }
}
