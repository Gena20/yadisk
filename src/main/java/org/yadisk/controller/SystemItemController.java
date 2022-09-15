package org.yadisk.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.yadisk.dto.SystemItemImportRequest;
import org.yadisk.exception.SystemItemInvalidTypeException;
import org.yadisk.exception.SystemItemNotFoundException;
import org.yadisk.service.HistoryItemService;
import org.yadisk.service.SystemItemService;

import javax.validation.Valid;
import java.time.Instant;

@RequiredArgsConstructor
@RestController
public class SystemItemController {

    private final SystemItemService systemItemService;

    private final HistoryItemService historyItemService;

    @PostMapping("/imports")
    public ResponseEntity<?> imports(@Valid @RequestBody SystemItemImportRequest request) throws SystemItemInvalidTypeException, SystemItemNotFoundException {
        systemItemService.importItems(request);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/nodes/{id}")
    public ResponseEntity<?> nodes(@PathVariable String id) throws SystemItemNotFoundException {
        return new ResponseEntity<>(systemItemService.getItemById(id), HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable String id, @RequestParam("date") Instant date) throws SystemItemNotFoundException {
        systemItemService.deleteItemById(id, date);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/updates")
    public ResponseEntity<?> updates(@RequestParam("date") Instant date) {
        return new ResponseEntity<>(historyItemService.getFilesByDate(date), HttpStatus.OK);
    }

    @GetMapping("/node/{id}/history")
    public ResponseEntity<?> nodeHistory(@PathVariable String id, @RequestParam("dateStart") Instant dateStart, @RequestParam("dateEnd") Instant dateEnd) throws SystemItemNotFoundException {
        return new ResponseEntity<>(historyItemService.getHistoryOfFileByDates(id, dateStart, dateEnd), HttpStatus.OK);
    }
}
