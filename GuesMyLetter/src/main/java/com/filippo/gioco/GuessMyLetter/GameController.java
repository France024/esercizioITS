package com.filippo.gioco.GuessMyLetter;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class GameController {

    private final GameService gameService;

    public GameController(GameService gameService) {
        this.gameService = gameService;
    }

    @GetMapping("/")
    public String home(Model model) {
        gameService.caricaClassifica();
        model.addAttribute("tentativiGiocatore", 0);
        model.addAttribute("nomeGiocatore", gameService.getNomeGiocatore());
        model.addAttribute("messaggio", "Indovina la lettera 'a' - 'z");
        model.addAttribute("classifica", gameService.getClassifica());
        return "index";
    }

    @PostMapping("/")
    public String tentativo(@RequestParam("nomeGiocatore") String nomeGiocatore,
                            @RequestParam("lettera") char lettera,
                            Model model) {
        String messaggio = gameService.processaTentativo(nomeGiocatore, lettera);
        model.addAttribute("tentativiGiocatore", gameService.getTentativiGiocatore());
        model.addAttribute("nomeGiocatore", gameService.getNomeGiocatore());
        model.addAttribute("messaggio", messaggio);
        model.addAttribute("classifica", gameService.getClassifica());
        return "index";
    }

    @PostMapping("/prossima-partita")
    public String giocaAncora(Model model) {
        gameService.resetGioco();
        model.addAttribute("tentativiGiocatore", 0);
        model.addAttribute("nomeGiocatore", "");
        model.addAttribute("messaggio", "Indovina la lettera 'a' - 'z'");
        model.addAttribute("classifica", gameService.getClassifica());
        return "index";
    }
    
    @GetMapping("/classifica")
    public String paginaClassifica(Model model) {
        model.addAttribute("classifica", gameService.getClassifica());  // Passiamo la lista della classifica alla pagina
        return "classifica";  // La tua pagina per la classifica
    }
}
