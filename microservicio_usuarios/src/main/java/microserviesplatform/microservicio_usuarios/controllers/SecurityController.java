package microserviesplatform.microservicio_usuarios.controllers;

import microserviesplatform.microservicio_usuarios.models.Usuario;
import microserviesplatform.microservicio_usuarios.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.Map;
import java.util.Optional;

@RestController
public class SecurityController {
    @Autowired
    private UsuarioRepository usuarioRepository;
    @GetMapping(path = "/authorized")
    public Map<String, String> authorized(@RequestParam String code){
        return Collections.singletonMap("code", code);
    }

    @GetMapping("/login")
    public ResponseEntity<?> loginByEmail(@RequestParam String email) {
        Optional<Usuario> o = usuarioRepository.findByEmail(email);
        if (o.isPresent()) {
            return ResponseEntity.ok(o.get());
        }
        return ResponseEntity.notFound().build();
    }

}
