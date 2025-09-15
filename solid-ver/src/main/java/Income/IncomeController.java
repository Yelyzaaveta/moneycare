package Income;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/incomes")
public class IncomeController {
    private final IncomeService service;

    public IncomeController(IncomeService service) {
        this.service = service;
    }

    @GetMapping
    public List<Income> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Income> getById(@PathVariable Long id) {
        return service.getById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Income create(@RequestBody Income income) {
        return service.create(income);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Income> update(@PathVariable Long id, @RequestBody Income income) {
        return service.update(id, income)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
