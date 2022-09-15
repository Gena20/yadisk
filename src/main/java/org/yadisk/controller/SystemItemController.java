package org.yadisk.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.yadisk.dto.SystemItemImportRequest;
import org.yadisk.exception.SystemItemInvalidTypeException;
import org.yadisk.exception.SystemItemNotFoundException;
import org.yadisk.service.SystemItemService;

import javax.validation.Valid;
import java.time.Instant;

@RequiredArgsConstructor
@RestController
public class SystemItemController {

    private final SystemItemService service;

    @PostMapping("/imports")
    public ResponseEntity<?> imports(@Valid @RequestBody SystemItemImportRequest request) throws SystemItemInvalidTypeException, SystemItemNotFoundException {
        service.importItems(request);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/nodes/{id}")
    public ResponseEntity<?> nodes(@PathVariable String id) throws SystemItemNotFoundException {
        return new ResponseEntity<>(service.getItemById(id), HttpStatus.OK);
    }

    // TODO: change return type??
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable String id, @RequestParam("date") Instant date) throws SystemItemNotFoundException {
        service.deleteItemById(id, date);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
