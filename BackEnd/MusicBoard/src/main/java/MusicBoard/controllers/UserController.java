package MusicBoard.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.collect.Lists;

import MusicBoard.entities.User;
import MusicBoard.entities.wrapper.Instruments;
import MusicBoard.entities.wrapper.Users;
import MusicBoard.repositories.UserRepository;

@RestController
@RequestMapping("user")
public class UserController {
    
    @Autowired
    private UserRepository userRepository;
    
    @GetMapping("")
    public ResponseEntity<Users> getAll(){
        final List<User> result = Lists.newArrayList();
        userRepository.findAll().iterator().forEachRemaining(result::add);
        return new ResponseEntity(Instruments.of(result), HttpStatus.OK);
    }
    
    @PostMapping("")
    public ResponseEntity<User> post(@RequestBody User user) {
        return ResponseEntity.ok(userRepository.save(user));
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<User> getById(@PathVariable Integer id){
       Optional<User> oUser = userRepository.findById(id);
        if (!oUser.isPresent()) {
            return ResponseEntity.notFound().build();
        } 
        return ResponseEntity.ok(oUser.get());
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<User> updateById(@PathVariable Integer id, @RequestBody User user) {
        Optional<User> oUser = userRepository.findById(id);
        if (oUser.isPresent()) {
            user.setId(id);
            return ResponseEntity.ok(userRepository.save(user));
        }
        return ResponseEntity.notFound().build();
    }
    
            
    @DeleteMapping("/{id}")
    public ResponseEntity<User> deleteById(@PathVariable Integer id) {
        Optional<User> oUser = userRepository.findById(id);
        if (!oUser.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        userRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }
    
    @GetMapping("/username/{userName}")
    public ResponseEntity<User> getByUserName(@PathVariable("userName") String userName){
       Optional<User> oUser = userRepository.findByUserName(userName);
        if (!oUser.isPresent()) {
            return ResponseEntity.notFound().build();
        } 
        return ResponseEntity.ok(oUser.get());
    }
    
    @PutMapping("/username/{userName}")
    public ResponseEntity<User> updateByName(@PathVariable("userName") String userName, @RequestBody User user) {
        Optional<User> oUser = userRepository.findByUserName(userName);
        if (oUser.isPresent()) {
            user.setId(oUser.get().getId());
            return ResponseEntity.ok(userRepository.save(user));
        }
        return ResponseEntity.notFound().build();
    }
    
            
    @DeleteMapping("/username/{userName}")
    public ResponseEntity<User> deleteByUserName(@PathVariable("userName") String userName) {
        Optional<User> oUser = userRepository.findByUserName(userName);
        if (!oUser.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        userRepository.delete(oUser.get());
        return ResponseEntity.ok().build();
    }
}
