package sk.aconsulting.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sk.aconsulting.dto.ItemDto;
import sk.aconsulting.dto.ItemResponse;
import sk.aconsulting.service.ItemService;

@RestController
@RequestMapping("/api/items")
public class ItemController {
    @Autowired
    private ItemService service;

    @Autowired
    public ItemController(ItemService service) {
        this.service = service;
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<ItemDto> createItem(@RequestBody ItemDto itemDto) {
        return new ResponseEntity<>(service.addItem(itemDto), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ItemDto> getItemById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(service.getItemById(id));
    }

    @GetMapping()
    public ResponseEntity<ItemResponse> getAllItems(
            @RequestParam(value = "pageNo", defaultValue = "0", required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize
    ) {
        return new ResponseEntity<>(service.getAllItems(pageNo, pageSize), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ItemDto> updateItem(@PathVariable("id") Long id, @RequestBody ItemDto itemDto) {
        ItemDto response = service.updateItem(id, itemDto);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteItem(@PathVariable("id") Long id) {
        try {
            service.deleteItem(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }

}
