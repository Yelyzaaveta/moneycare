package Income;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/*
  @author Orynchuk
  @project moneycare
  @class IncomeController
  @version 1.0.0
  @since 15.09.2025 - 14.25
*/

import org.springframework.security.access.prepost.PreAuthorize;

@RestController
@RequestMapping("/api/v1/incomes")
public class IncomeController {
    private final IncomeService service;

    public IncomeController(IncomeService service) {
        this.service = service;
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('USER','ADMIN','SUPERADMIN')")
    public List<Income> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('USER','ADMIN','SUPERADMIN')")
    public ResponseEntity<Income> getById(@PathVariable Long id) {
        return service.getById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN','SUPERADMIN')")
    public Income create(@RequestBody Income income) {
        return service.create(income);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','SUPERADMIN')")
    public ResponseEntity<Income> update(@PathVariable("id") Long id, @RequestBody Income income)
    {
        return service.update(id, income)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('SUPERADMIN')")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/public")
    public String publicAccess() {
        return "Public endpoint — everyone can see this.";
    }

    @GetMapping("/user")
    @PreAuthorize("hasRole('USER')")
    public String userAccess() {
        return "User endpoint — USER role only.";
    }

    @GetMapping("/admin")
    @PreAuthorize("hasRole('ADMIN')")
    public String adminAccess() {
        return "Admin endpoint — ADMIN role only.";
    }

    @GetMapping("/superadmin")
    @PreAuthorize("hasRole('SUPERADMIN')")
    public String superAdminAccess() {
        return "SuperAdmin endpoint — SUPERADMIN only.";
    }

    @GetMapping("/mixed")
    @PreAuthorize("hasAnyRole('ADMIN','SUPERADMIN')")
    public String mixedAccess() {
        return "Mixed endpoint — ADMIN or SUPERADMIN.";
    }
}
