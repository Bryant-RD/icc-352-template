package blog.encapsulaciones;

import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table
public class LogUser {

    @Id
    @Column
    private String userId;
    @Column

    private String username;
    @Column

    private String logDate;

    public LogUser () {}

    public LogUser (String userId, String username, String logDate) {

        this.userId = userId;
        this.username = username;
        this.logDate = logDate;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }

    public String getLogDate() {
        return logDate;
    }
    public void setLogDate(String logDate) {
        this.logDate = logDate;
    }

    

}
