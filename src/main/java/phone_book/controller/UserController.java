package phone_book.controller;

import phone_book.domain.Person;
import phone_book.domain.PhoneNumber;
import phone_book.domain.Street;
import phone_book.repos.PhoneRepository;
import phone_book.repos.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class UserController {

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private PhoneRepository phoneRepository;

    //Методы следует называть согласно их функции
    @GetMapping()
    public String search(@RequestParam(name = "q", required = false) String quest, Model model) {
        //List<User> users;

        //Не самый удачный вариант поиска, представь, что будет если в таблице будет 1000000000 пользователей.
        String likeCondition = quest == null ? "%" : quest+"%";
        model.addAttribute("userList", personRepository.findAllByNameLAndNumber(likeCondition, likeCondition));
//        if(quest == null){
//            users = userRepository.findAll();
//        }
//        else {
//            users = new ArrayList<>();
//          for(User u : userRepository.findAll()){
//              if(u.getName().toLowerCase().contains(quest.toLowerCase()))
//                  users.add(u);
//          }
//          for(PhoneNumber n : phoneRepository.findAll()){
//              if(n.getNumber().replaceAll("\\D","").contains(quest.replaceAll("\\D","")))
//                  users.add(n.getUser());
//          }
//        }
//        model.addAttribute("userList", users);
        return "main";
    }

    @PostMapping
    public String addNewUser(@RequestParam String new_user, Model model) {
        Person person = Person.getNewUser();
        person.setName(new_user);
        personRepository.save(person);
        return "redirect:/";
    }


    @GetMapping("/person")
    public String getPerson(@RequestParam String id, Model model) {
        Person person = personRepository.getOne(Integer.parseInt(id));
        model.addAttribute("person", person);
        List<PhoneNumber> numbers = phoneRepository.findAllByPerson(person);
        model.addAttribute("numbers", numbers);
        model.addAttribute("streets", Street.getAllStreet());
        return "person";
    }


    @PostMapping("/person")
    public String addNumber(@RequestParam String type, @RequestParam String number, @RequestParam String id, Model model) {
        PhoneNumber number1 = new PhoneNumber();
        number1.setType(type);
        number1.setNumber(number);
        Person person = personRepository.getOne(Integer.parseInt(id));
        number1.setPerson(person);
        person.addNumber(number1);
        phoneRepository.save(number1);
        personRepository.save(person);
        return getPerson(id, model);
    }

    @GetMapping("/person/delete")
    public String deleteNumber(@RequestParam(name = "idNumber") String idNumber, Model model) {
        int phn = Integer.parseInt(idNumber);
        PhoneNumber phoneNumber = phoneRepository.getOne(phn);
        Person u = phoneNumber.getPerson();
        phoneRepository.delete(phoneNumber);
        u.getNumbers().remove(phoneNumber);
        return "redirect:/person?id=" + u.getId();
    }

    @GetMapping("/delete")
    public String deletePerson(@RequestParam String id) {

        personRepository.delete(personRepository.getOne(Integer.parseInt(id)));
        return "redirect:/";
    }

    /**
     * В названиях переменных стоит использовать camelCase нотацию
     * @return
     */
    @GetMapping("/save")
    public String saveUser(@RequestParam(name = "name") String nameUser,
                           @RequestParam(name = "house") String houseNumber,
                           @RequestParam(name = "id") String userId,
                           @RequestParam(name = "street") String userStreet,
                           @RequestParam(name = "room") String userRoom,
                           Model model) {
        //Не совсем понял зачем это нужно
//        if (userId.equals("0")) {
//            for (Person u : personRepository.findAll()) {
//                if (u.getName().equals("-"))
//                    userId = String.valueOf(u.getId());
//            }
//        }
        Person person = personRepository.getOne(Integer.parseInt(userId));
        person.setName(nameUser);
        person.setHouseNumber(Integer.parseInt(houseNumber));
        person.setRoomNumber(Integer.parseInt(userRoom));
        person.setStreet(userStreet);
        personRepository.save(person);
        return "redirect:/";
    }

}