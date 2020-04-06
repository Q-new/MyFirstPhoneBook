package phone_book.controller;

import phone_book.domain.PhoneNumber;
import phone_book.domain.Street;
import phone_book.domain.User;
import phone_book.repos.PhoneRepository;
import phone_book.repos.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
public class UserController {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PhoneRepository phoneRepository;

    @GetMapping
    public String main(@RequestParam(name="q", required = false) String quest, Model model){
        List<User> users;

        if(quest == null){
            users = userRepository.findAll();
        }
        else {
            users = new ArrayList<>();
          for(User u : userRepository.findAll()){
              if(u.getName().toLowerCase().contains(quest.toLowerCase()))
                  users.add(u);
          }
          for(PhoneNumber n : phoneRepository.findAll()){
              if(n.getNumber().replaceAll("\\D","").contains(quest.replaceAll("\\D","")))
                  users.add(n.getUser());
          }
        }
        model.addAttribute("userList", users);
        return "main";
    }

    @PostMapping
    public String addNewUser(@RequestParam String new_user,Model model){
        User user = User.getNewUser();
        user.setName(new_user);
        userRepository.save(user);
        return "redirect:/";
    }


    @GetMapping("/user")
    public String getUser(@RequestParam String id, Model model){
        User user = userRepository.getOne(Integer.parseInt(id));
        model.addAttribute("user", user);
        List<PhoneNumber> numbers = phoneRepository.findAllByUser(user);
        model.addAttribute("numbers", numbers);
        model.addAttribute("streets", Street.getAllStreet());
        return "user";
    }


    @PostMapping("/user")
    public String addNumber(@RequestParam String type, @RequestParam String number,@RequestParam String id, Model model){
        PhoneNumber number1 = new PhoneNumber();
        number1.setType(type);
        number1.setNumber(number);
        User user = userRepository.getOne(Integer.parseInt(id));
        number1.setUser(user);
        user.addNumber(number1);
        phoneRepository.save(number1);
        userRepository.save(user);
        return getUser(id,model);
    }

    @GetMapping("/user/delete")
    public String deleteNumber(@RequestParam(name = "idNumber") String idNumber, Model model){
        int phn = Integer.parseInt(idNumber);
       PhoneNumber phoneNumber = phoneRepository.getOne(phn);
        User u = phoneNumber.getUser();
        phoneRepository.delete(phoneNumber);
        u.getNumbers().remove(phoneNumber);
        return "redirect:/user?id=" + u.getId();
    }

    @GetMapping("/delete")
    public String deleteUser(@RequestParam String id){

        userRepository.delete(userRepository.getOne(Integer.parseInt(id)));
        return "redirect:/";
    }

    @GetMapping("/save")
    public String saveUser(@RequestParam(name = "name") String name_user,
                           @RequestParam(name = "house") String age_house,
                           @RequestParam(name = "id") String user_id,
                           @RequestParam(name = "street") String user_street,
                           @RequestParam(name = "room") String user_room,
                           Model model){
        if(user_id.equals("0")){
            for (User u : userRepository.findAll()){
                if (u.getName().equals("-"))
                    user_id = String.valueOf(u.getId());
            }
        }
        User user = userRepository.getOne(Integer.parseInt(user_id));
        user.setName(name_user);
        user.setHouseNumber(Integer.parseInt(age_house));
        user.setRoomNumber(Integer.parseInt(user_room));
        user.setStreet(user_street);
        userRepository.save(user);
        return "redirect:/";
    }

}