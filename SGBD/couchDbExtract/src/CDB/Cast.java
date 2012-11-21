/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package CDB;



public class Cast{
    private String name;
    private String job;
    private String department;
    private String character;
    private int id;
    private int order;
    private int cast_id;
    private String url;
    private String profile;

    public Cast(){
        name = null;
        job = null;
        department = null;
        character = null;
        id = -1;
        order = -1;
        cast_id = -1;
        url = null;
        profile = null;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getCharacter() {
        return character;
    }

    public void setCharacter(String character) {
        this.character = character;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public int getCast_id() {
        return cast_id;
    }

    public void setCast_id(int cast_id) {
        this.cast_id = cast_id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
