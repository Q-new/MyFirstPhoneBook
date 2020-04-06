package phone_book.domain;


import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class User {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String name;
    private String street;
    private int houseNumber;
    private int roomNumber;
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<PhoneNumber> numbers;

    public User() {
    }

    public User(String name, int age) {
        this.name = name;
       this.numbers = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<PhoneNumber> getNumbers() {
        return numbers;
    }

    public void setNumbers(List<PhoneNumber> numbers) {
        this.numbers = numbers;
    }

    public void addNumber(PhoneNumber number){
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public int getHouseNumber() {
        return houseNumber;
    }

    public void setHouseNumber(int houseNumber) {
        this.houseNumber = houseNumber;
    }

    public int getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(int roomNumber) {
        this.roomNumber = roomNumber;
    }

    public static User getNewUser(){
        User user = new User();
        user.setName("");
        user.setStreet(Street.getAllStreet().get(0));
        user.setHouseNumber(0);
        user.setRoomNumber(0);
        return user;
    }
}
