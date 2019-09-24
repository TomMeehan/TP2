package ticketmachine;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class TicketMachineTest {
	private static final int PRICE = 50; // Une constante

	private TicketMachine machine; // l'objet à tester

	@Before
	public void setUp() {
		machine = new TicketMachine(PRICE); // On initialise l'objet à tester
	}

	@Test
	// On vérifie que le prix affiché correspond au paramètre passé lors de l'initialisation
	// S1 : le prix affiché correspond à l’initialisation
	public void priceIsCorrectlyInitialized() {
		// Paramètres : message si erreur, valeur attendue, valeur réelle
		assertEquals("Initialisation incorrecte du prix", PRICE, machine.getPrice());
	}

	@Test
	// S2 : la balance change quand on insère de l’argent
	public void insertMoneyChangesBalance() {
		machine.insertMoney(10);
		machine.insertMoney(20);
		assertEquals("La balance n'est pas correctement mise à jour", 10 + 20, machine.getBalance()); // Les montants ont été correctement additionnés               
	}
        
        @Test
        // S3 : on n'imprime pas le ticket si le montant inséré est insuffisant
        public void dontPrintIfBalanceTooLow() {
                machine.insertMoney(PRICE-1);
                assertFalse(machine.printTicket());
        }
        
        @Test
        // S4 : on imprime le ticket si la balance est suffisante
        public void printIfEnoughBalance() {
                machine.insertMoney(PRICE);
                assertTrue(machine.printTicket());
        }
        
        @Test
        // S5 : la balance est bien décrémenté apès impression
        public void balanceUpdatedAfterPrint() {
                machine.insertMoney(60);
                machine.printTicket();
                assertEquals("La balance n'est pas mise a jour par l'impression du ticket",60-PRICE,machine.getBalance());
        }
        
        @Test
        // S6 : le montantle montant collecté est mis à jour quand on imprime un ticket (pas avant)
        public void totalUpdatedAfterPrint() {
                machine.insertMoney(60);
                assertEquals("Le total est mis a jour a l'insertion",0,machine.getTotal());
                machine.printTicket();
                assertEquals("Le total n'est pas correctement mis a jour apres impression",PRICE,machine.getTotal());
        }
        
        @Test
        // S7 refund() rend correctement la monnaie
        public void refundWorks() {
                machine.insertMoney(30);
                assertEquals("La machine ne rend pas correctement la monnaie",30,machine.refund());
        }
        
        @Test
        // S8 refund() remet la balance à zero
        public void refundResetsBalance() {
                machine.insertMoney(30);
                machine.refund();
                assertEquals("La balance n'est pas correctement remise à zero",0,machine.getBalance());
        }
        
        @Test
        // S9 on ne peut pas insérer un montant négatif
        public void cannotInsertNegativeAmount() {
                try{
                    machine.insertMoney(-10);
                    fail("Montant négatif inséré, pas d'exception");
                }
                catch (IllegalArgumentException e){
                    //Good
                }
        }
        
        @Test
        // S10 on ne peut pas créer de machine qui délivre des tickets dont le prix est négatif
        public void cannotCreateWithNegativePrice() {
                try{
                    TicketMachine machineTemp = new TicketMachine(-50);
                    fail("Machine créée avec un prix de ticket négatif, pas d'exception");
                }
                catch (IllegalArgumentException e){
                    
                }
        }
}
