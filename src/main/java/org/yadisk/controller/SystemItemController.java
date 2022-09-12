package org.yadisk.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class SystemItemController {

    @PostMapping("/imports")
    public ResponseEntity<?> imports() throws UnsupportedOperationException {
        throw new UnsupportedOperationException("Not implemented");
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable String id) throws UnsupportedOperationException {
        throw new UnsupportedOperationException("Not implemented");
    }

    @GetMapping("/nodes/{id}")
    public ResponseEntity<?> nodes(@PathVariable String id) throws UnsupportedOperationException {
        throw new UnsupportedOperationException("Not implemented");
    }
}
