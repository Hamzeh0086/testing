package oslomet.testing;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import oslomet.testing.API.BankController;
import oslomet.testing.DAL.BankRepository;
import oslomet.testing.Models.Konto;
import oslomet.testing.Models.Kunde;
import oslomet.testing.Models.Transaksjon;
import oslomet.testing.Sikkerhet.Sikkerhet;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class EnhetstestBankController {

    @InjectMocks
    // denne skal testes
    private BankController bankController;

    @Mock
    // denne skal Mock'es
    private BankRepository repository;

    @Mock
    // denne skal Mock'es
    private Sikkerhet sjekk;

    @Test
    public void hentKundeInfo_loggetInn() {

        // arrange
        Kunde enKunde = new Kunde("01010110523",
                "Lene", "Jensen", "Askerveien 22", "3270",
                "Asker", "22224444", "HeiHei");

        when(sjekk.loggetInn()).thenReturn("01010110523");

        when(repository.hentKundeInfo(anyString())).thenReturn(enKunde);

        // act
        Kunde resultat = bankController.hentKundeInfo();

        // assert
        assertEquals(enKunde, resultat);
    }

    @Test
    public void hentKundeInfo_IkkeloggetInn() {

        // arrange
        when(sjekk.loggetInn()).thenReturn(null);

        //act
        Kunde resultat = bankController.hentKundeInfo();

        // assert
        assertNull(resultat);
    }

    @Test
    public void hentKonti_LoggetInn()  {
        // arrange
        List<Konto> konti = new ArrayList<>();
        List<Transaksjon> transaksjonList = new ArrayList<>();
        Transaksjon transaksjon1 = new Transaksjon(1, "01234567890", 12, "15012021", "Mat", "0", "12345678901");
        Transaksjon transaksjon2 = new Transaksjon(2, "98765432109", 50, "20012021", "PC", "1", "87654321098");

        Konto konto1 = new Konto("105010123456", "01010110523",
        720, "Lønnskonto", "NOK", transaksjonList);

        Konto konto2 = new Konto("105010123456", "12345678901",
                1000, "Lønnskonto", "NOK", transaksjonList);
        konti.add(konto1);
        konti.add(konto2);

        when(sjekk.loggetInn()).thenReturn("01010110523");

        Mockito.when(repository.hentKonti(anyString())).thenReturn(konti);

        // act
        List<Konto> resultat = bankController.hentKonti();

        // assert
        assertEquals(konti, resultat);
    }

    @Test
    public void hentKonti_IkkeLoggetInn()  {
        // arrange

        when(sjekk.loggetInn()).thenReturn(null);

        // act
        List<Konto> resultat = bankController.hentKonti();

        // assert
        assertNull(resultat);
    }

    //Hitomi
    @Test
    public void hentTransaksjoner_LoggetInn(){
        // arrange
        List<Transaksjon> transaksjonList = new ArrayList<>();
        Transaksjon transaksjon1 = new Transaksjon(1, "01234567890", 12, "15012021", "Mat", "0", "12345678901");
        Transaksjon transaksjon2 = new Transaksjon(2, "98765432109", 50, "20012021", "PC", "1", "87654321098");

        Konto konto1 = new Konto("03010098765", "12345678901",
                20000, "Sparekonto", "NOK", transaksjonList);

        when(sjekk.loggetInn()).thenReturn("01010110523");

        Mockito.when(repository.hentTransaksjoner(anyString(),anyString(),anyString())).thenReturn(konto1);

        // act

        Konto resultat = bankController.hentTransaksjoner("12345678901", "01012000", "01012100");

        // assert
        assertEquals(konto1, resultat);
    }

    //Hitomi
    @Test
    public void hentTransaksjoner_IkkLoggetInn(){

        // arrange

        when(sjekk.loggetInn()).thenReturn(null);

        // act
        Konto resultat = bankController.hentTransaksjoner("12345678901", "01012000", "01012100");

        // assert
        assertNull(resultat);
    }

    //Hitomi
    @Test
    public void hentSaldi_LoggetInn(){

        List<Konto> saldiList = new ArrayList<>();
        List<Transaksjon> transaksjonList = new ArrayList<>();
        transaksjonList.add( new Transaksjon(1, "01234567890", 12, "15012021", "Mat", "0", "12345678901"));

        Konto konto1 = new Konto("05010187654", "56789012345",
                20000, "Sparekonto", "NOK", transaksjonList);
        Konto konto2 = new Konto("06010276543", "67890123456",
                10000, "Dagligkonto", "NOK", transaksjonList);

        saldiList.add(konto1);
        saldiList.add(konto2);

        //
        when(sjekk.loggetInn()).thenReturn("01010110523");
        Mockito.when(repository.hentSaldi(anyString())).thenReturn(saldiList);

        //
        List<Konto> resultat = bankController.hentSaldi();

        // assert
        assertEquals(saldiList, resultat);
    }

    //Hitomi
    @Test
    public void hentSaldi_IkkLoggetInn(){
        // arrange

        when(sjekk.loggetInn()).thenReturn(null);

        // act
        List<Konto> resultat = bankController.hentSaldi();

        // assert
        assertNull(resultat);
    }

// Hamzeh part
    @Test
    public void registrerBetaling_LoggetInn(){

        Transaksjon transaksjon = new Transaksjon(1, "01234567890", 12, "15012021", "Mat", "1", "12345678901");


        when(sjekk.loggetInn()).thenReturn("11111111111");

        when(repository.registrerBetaling(any())).thenReturn("OK");



        String resultat = bankController.registrerBetaling(transaksjon);

        assertEquals("OK", resultat);
    }
    @Test
    public void registrerBetaling_IkkLoggetInn(){

        when(sjekk.loggetInn()).thenReturn(null);

        String resultat = bankController.registrerBetaling(null);

        assertEquals(null, resultat);
    }

    @Test
    public void utforBetaling_LoggetInn(){
    List<Transaksjon> transaksjons = new ArrayList<>();
    Transaksjon transaksjon1 = new Transaksjon(1, "01234567890", 12, "15012021", "Mat", "1", "12345678901");
    Transaksjon transaksjon2 = new Transaksjon(2,"34567890123",60,"16012021","Strøm", "1", "67890123456");
    transaksjons.add(transaksjon1);
    transaksjons.add(transaksjon2);

    when(sjekk.loggetInn()).thenReturn("01010110523");
    when(repository.utforBetaling(anyInt())).thenReturn("OK");
    when(repository.hentBetalinger(anyString())).thenReturn(transaksjons);

        // act
        List<Transaksjon> resultat = bankController.utforBetaling(1);

        // assert
        assertEquals(transaksjons, resultat);
    }
    @Test
    public void utforBetaling_IkkLoggetInn(){
        // arrange

        when(sjekk.loggetInn()).thenReturn(null);

        // act
        List<Transaksjon> resultat = bankController.utforBetaling(1);

        // assert
        assertNull(resultat);
    }

//Maja
    @Test
    public void hentBetalinger_LoggetInn(){

        List<Transaksjon> transaksjonList = new ArrayList<>();
        Transaksjon transaksjon1 = new Transaksjon(1, "01234567890", 12, "15012021", "Mat", "1", "12345678901");
        Transaksjon transaksjon2 = new Transaksjon(2,"34567890123",60,"16012021","Strøm", "1", "67890123456");

        transaksjonList.add(transaksjon1);
        transaksjonList.add(transaksjon2);

        when(sjekk.loggetInn()).thenReturn("01010110523");
        Mockito.when(repository.hentBetalinger(anyString())).thenReturn(transaksjonList);

        List<Transaksjon> resultat = bankController.hentBetalinger();

        assertEquals(transaksjonList, resultat);

    }
    @Test
    public void hentBetalinger_IkkLoggetInn(){

        when (sjekk.loggetInn()).thenReturn(null);

        List<Transaksjon> resultat = bankController.hentBetalinger();

        assertNull(resultat);

    }

    @Test
    public void endreKundeInfo_LoggetInn(){
        Kunde kunde1 = new Kunde("01010110523",
                "Lene", "Jensen", "Askerveien 22", "3270",
                "Asker", "22224444", "HeiHei");

        when(sjekk.loggetInn()).thenReturn("11111111111");

        Mockito.when(repository.endreKundeInfo(any(Kunde.class))).thenReturn("OK");

        String resultat = bankController.endre(kunde1);
        Assert.assertEquals("OK", resultat);
    }

    @Test
    public void endreKundeInfo_IkkLoggetInn(){

        Kunde kunde1 = new Kunde("01010110523",
                "Lene", "Jensen", "Askerveien 22", "3270",
                "Asker", "22224444", "HeiHei");

        when(sjekk.loggetInn()).thenReturn(null);

        String resultat = bankController.endre(kunde1);

        assertNull(resultat);
    }
}

