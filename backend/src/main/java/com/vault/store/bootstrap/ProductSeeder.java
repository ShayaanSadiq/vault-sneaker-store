package com.vault.store.bootstrap;

import com.vault.store.domain.Product;
import com.vault.store.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ProductSeeder implements ApplicationRunner {

    private final ProductRepository productRepository;

    @Override
    public void run(ApplicationArguments args) {
        if (productRepository.count() > 0) {
            return;
        }

        productRepository.saveAll(List.of(
                product("apex-infrared", "Apex Infrared", 189, "Running", "New Drop",
                        "Responsive foam midsole and a breathable knit upper built for daily miles.",
                        "https://images.unsplash.com/photo-1542291026-7eec264c27ff?auto=format&fit=crop&w=900&q=80"),
                product("court-shadow", "Court Shadow", 149, "Basketball", "",
                        "Low-cut court shoe with herringbone traction and a padded collar.",
                        "https://images.unsplash.com/photo-1608231387042-66d1773070a5?auto=format&fit=crop&w=900&q=80"),
                product("pulse-high", "Pulse High", 219, "Limited", "Limited",
                        "High-top drop with pastel blocking and a padded ankle.",
                        "https://images.unsplash.com/photo-1595950653106-6c9ebd614d3a?auto=format&fit=crop&w=900&q=80"),
                product("drift-mono", "Drift Mono", 129, "Lifestyle", "",
                        "Minimal leather sneaker in warm gum and cream.",
                        "https://images.unsplash.com/photo-1549298916-b41d501d3772?auto=format&fit=crop&w=900&q=80"),
                product("trail-ember", "Trail Ember", 179, "Running", "",
                        "Trail-ready outsole with rock plate protection.",
                        "https://images.unsplash.com/photo-1460353581641-37baddab0fa2?auto=format&fit=crop&w=900&q=80"),
                product("dunk-glacier", "Dunk Glacier", 159, "Lifestyle", "Restock",
                        "Classic cupsole with icy white leather and a chunky foxing.",
                        "https://images.unsplash.com/photo-1600185365483-26d7a4cc7519?auto=format&fit=crop&w=900&q=80"),
                product("runner-void", "Runner Void", 199, "Running", "",
                        "Volt-green racing silhouette with a featherweight mesh upper.",
                        "https://images.unsplash.com/photo-1606107557195-0e29a4b5b4aa?auto=format&fit=crop&w=900&q=80"),
                product("force-solar", "Force Solar", 169, "Basketball", "",
                        "Heritage basketball last with premium tumbled leather.",
                        "https://images.unsplash.com/photo-1552346154-21d32810aba3?auto=format&fit=crop&w=900&q=80"),
                product("midnight-retro", "Midnight Retro", 209, "Limited", "Sold Fast",
                        "All-black retro runner with reflective hits.",
                        "https://images.unsplash.com/photo-1514989940723-e8e51635b782?auto=format&fit=crop&w=900&q=80"),
                product("wave-knit", "Wave Knit", 139, "Lifestyle", "",
                        "Sock-like knit upper with a sculpted foam midsole.",
                        "https://images.unsplash.com/photo-1560769629-975ec94e6a86?auto=format&fit=crop&w=900&q=80"),
                product("sage-low", "Sage Low", 154, "Lifestyle", "New Drop",
                        "Muted sage suede with a cream midsole.",
                        "https://images.unsplash.com/photo-1603808033192-082d6919d3e1?auto=format&fit=crop&w=900&q=80"),
                product("chrome-max", "Chrome Max", 174, "Running", "",
                        "Visible air-style units and a chrome-tinted overlay.",
                        "https://images.unsplash.com/photo-1539185441755-769473a23570?auto=format&fit=crop&w=900&q=80")
        ));
    }

    private Product product(String id, String name, double price, String category, String badge,
                            String description, String image) {
        return Product.builder()
                .id(id)
                .name(name)
                .brand("VAULT")
                .price(price)
                .category(category)
                .badge(badge)
                .description(description)
                .image(image)
                .build();
    }
}
