package com.samba.springbootsuperheroes.user.service;

import com.samba.springbootsuperheroes.exception.NotFoundException;
import com.samba.springbootsuperheroes.user.dto.UserDto;
import com.samba.springbootsuperheroes.user.entity.UserEntity;
import com.samba.springbootsuperheroes.user.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
@AllArgsConstructor
@Service
public class UserService {

    private  ModelMapper modelMapper;
    public final UserRepository userRepository;
    private UserDto convertToDto(UserEntity userEntity) {
        return modelMapper.map(userEntity, UserDto.class);
    }
    private UserEntity convertToEntity(UserDto userDto) {
        return modelMapper.map(userDto, UserEntity.class);
    }

    public List<UserDto> getUsers() {
        var userEntityList = new ArrayList<>(userRepository.findAll());
        return userEntityList.stream().map(this::convertToDto).collect(Collectors.toList());
    }

    public UserDto getUserById(Integer id) {
        var user = userRepository.findById(id).orElseThrow(() -> new NotFoundException("User by id " + id + " was not found"));
        return convertToDto(user);
    }

    private byte[] createSalt() {
    var salt =new SecureRandom();
    byte[] saltBytes = new byte[16];
    salt.nextBytes(saltBytes);
    return saltBytes;
    }
    private byte[] createPasswordHash(String password, byte[] salt) throws NoSuchAlgorithmException {
        var md = MessageDigest.getInstance("SHA-512");
        md.update(salt);
        return md.digest(password.getBytes(StandardCharsets.UTF_8));
    }

    public UserDto createUser(UserDto userDto, String password) throws NoSuchAlgorithmException {
        var user = convertToEntity(userDto);
        if (password.isBlank()){
            throw new IllegalStateException("password cannot be empty");
        }

        if (userRepository.selectExistsByEmail(user.getEmail())) {
            throw new IllegalStateException("email"+ user.getEmail() + "is already taken");
        }
        byte[] salt = createSalt();
        byte[] passwordHash = createPasswordHash(password, salt);
        user.setStoredSalt(salt);
        user.setStoredHash(passwordHash);

         userRepository.save(user);
        return convertToDto(user);
    }

    public void updateUser(Integer id, UserDto userDto, String password)
            throws NoSuchAlgorithmException {
        var user = findOrThrow(id);
        var userParam = convertToEntity(userDto);
        user.setEmail(userParam.getEmail());
        user.setMobileNumber(userParam.getMobileNumber());
        if (!password.isBlank()) {
            byte[] salt = createSalt();
            byte[] hashedPassword =
                    createPasswordHash(password, salt);
            user.setStoredSalt(salt);
            user.setStoredHash(hashedPassword);
        }
        userRepository.save(user);
    }
    public void removeUserById(Integer id) {
        findOrThrow(id);
        userRepository.deleteById(id);
    }
    private UserEntity findOrThrow(final Integer id) {
        return userRepository
                .findById(id)
                .orElseThrow(
                        () -> new NotFoundException("User by id " + id +
                                " was not found")
                );
    }

    public Boolean searchUser(String email){
        return userRepository.selectExistsByEmail(email);
    }

    public UserEntity findByEmail(String email) {
        if (userRepository.selectExistsByEmail(email)) {
            return userRepository.findByEmail(email);
        }
        throw new IllegalStateException("email"+ email + "is not registered");
    }
}
