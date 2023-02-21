package com.utcn.models;

public class Raport_Medical {
    int id_programare;
    String  cnp_asistent, cnp_medic, diagnostic, recomandari, simptome, nume_medic_recomandat,
            nume_medic_consultant, nume_asistent, prenume_medic_recomandat, prenume_medic_consultant,
            prenume_asistent;

    public Raport_Medical(int id_programare, String cnp_asistent, String cnp_medic, String diagnostic, String recomandari,
                          String simptome, String nume_medic_recomandat, String nume_medic_consultant, String nume_asistent,
                          String prenume_medic_recomandat, String prenume_medic_consultant, String prenume_asistent) {
        this.id_programare = id_programare;
        this.cnp_asistent = cnp_asistent;
        this.cnp_medic = cnp_medic;
        this.diagnostic = diagnostic;
        this.recomandari = recomandari;
        this.simptome = simptome;
        this.nume_medic_recomandat = nume_medic_recomandat;
        this.nume_medic_consultant = nume_medic_consultant;
        this.nume_asistent = nume_asistent;
        this.prenume_medic_recomandat = prenume_medic_recomandat;
        this.prenume_medic_consultant = prenume_medic_consultant;
        this.prenume_asistent = prenume_asistent;
    }

    public int getId_programare() {
        return id_programare;
    }

    public void setId_programare(int id_programare) {
        this.id_programare = id_programare;
    }

    public String getCnp_asistent() {
        return cnp_asistent;
    }

    public void setCnp_asistent(String cnp_asistent) {
        this.cnp_asistent = cnp_asistent;
    }

    public String getCnp_medic() {
        return cnp_medic;
    }

    public void setCnp_medic(String cnp_medic) {
        this.cnp_medic = cnp_medic;
    }

    public String getDiagnostic() {
        return diagnostic;
    }

    public void setDiagnostic(String diagnostic) {
        this.diagnostic = diagnostic;
    }

    public String getRecomandari() {
        return recomandari;
    }

    public void setRecomandari(String recomandari) {
        this.recomandari = recomandari;
    }

    public String getSimptome() {
        return simptome;
    }

    public void setSimptome(String simptome) {
        this.simptome = simptome;
    }

    public String getNume_medic_recomandat() {
        return nume_medic_recomandat;
    }

    public void setNume_medic_recomandat(String nume_medic_recomandat) {
        this.nume_medic_recomandat = nume_medic_recomandat;
    }

    public String getNume_medic_consultant() {
        return nume_medic_consultant;
    }

    public void setNume_medic_consultant(String nume_medic_consultant) {
        this.nume_medic_consultant = nume_medic_consultant;
    }

    public String getNume_asistent() {
        return nume_asistent;
    }

    public void setNume_asistent(String nume_asistent) {
        this.nume_asistent = nume_asistent;
    }

    public String getPrenume_medic_recomandat() {
        return prenume_medic_recomandat;
    }

    public void setPrenume_medic_recomandat(String prenume_medic_recomandat) {
        this.prenume_medic_recomandat = prenume_medic_recomandat;
    }

    public String getPrenume_medic_consultant() {
        return prenume_medic_consultant;
    }

    public void setPrenume_medic_consultant(String prenume_medic_consultant) {
        this.prenume_medic_consultant = prenume_medic_consultant;
    }

    public String getPrenume_asistent() {
        return prenume_asistent;
    }

    public void setPrenume_asistent(String prenume_asistent) {
        this.prenume_asistent = prenume_asistent;
    }

}
