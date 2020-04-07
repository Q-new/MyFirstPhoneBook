package phone_book.domain;

import javax.persistence.*;

@Entity
public class PhoneNumber {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String type;
    private String number;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private Person person;

    public PhoneNumber(String type, String number, Person person) {
        this.type = type;
        this.number = number;
        this.person = person;
    }

    public PhoneNumber() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    //Если ипользуется MVC, то форматировать вывод данных лучше на вью.
    public String getNumber() {
       // StringBuffer result = new StringBuffer(number.substring(0,1)).append(" (")
       //         .append(number.substring(1,4)).append(") ").append(number.substring(4,number.length()));
       // return result.toString();
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

}
