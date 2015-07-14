/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.ecos.statusquo.persistence.entities;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Dev
 */
@Entity
@Table(name = "EPISODIO")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Episodio.findAll", query = "SELECT e FROM Episodio e"),
    @NamedQuery(name = "Episodio.findById", query = "SELECT e FROM Episodio e WHERE e.id = :id"),
    @NamedQuery(name = "Episodio.findByFecha", query = "SELECT e FROM Episodio e WHERE e.fecha = :fecha"),
    @NamedQuery(name = "Episodio.findByRangoFecha", query = "SELECT e FROM Episodio e WHERE e.fecha between :fechaInicio and :fechaFin"),
    @NamedQuery(name = "Episodio.findByMedicamentos", query = "SELECT e FROM Episodio e WHERE e.medicamentos = :medicamentos"),
    @NamedQuery(name = "Episodio.findByNiveldolor", query = "SELECT e FROM Episodio e WHERE e.nivelDolor = :niveldolor"),
    @NamedQuery(name = "Episodio.findByPatronessueno", query = "SELECT e FROM Episodio e WHERE e.patronesSueno = :patronessueno"),
    @NamedQuery(name = "Episodio.findByActividades", query = "SELECT e FROM Episodio e WHERE e.actividades = :actividades"),
    @NamedQuery(name = "Episodio.findByPaciente", query = "SELECT e FROM Episodio e WHERE e.paciente = :paciente"),
    @NamedQuery(name = "Episodio.findByIdRangoFecha", query = "SELECT e FROM Episodio e WHERE e.paciente = :paciente and e.fecha between :fechaInicio and :fechaFin")})
public class Episodio implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @SequenceGenerator(name = "SeqEpisodio", sequenceName = "SEQ_EPISODIO", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SeqEpisodio")
    @Basic(optional = false)
    @Column(name = "ID", nullable = false)
    private Long id;
    @Column(name = "FECHA")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fecha;
    @Size(max = 4000)
    @Column(name = "MEDICAMENTOS", length = 4000)
    private String medicamentos;
    @Size(max = 4000)
    @Column(name = "NIVEL_DOLOR", length = 4000)
    private String nivelDolor;
    @Size(max = 4000)
    @Column(name = "PATRONES_SUENO", length = 4000)
    private String patronesSueno;
    @Size(max = 4000)
    @Column(name = "ACTIVIDADES", length = 4000)
    private String actividades;
    @OneToMany(mappedBy = "episodio")
    private List<Diagnostico> diagnosticos;
    @JoinColumn(name = "PACIENTE", referencedColumnName = "ID")
    @ManyToOne
    private Paciente paciente;
    @OneToMany(mappedBy = "episodio", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<AudioEpisodio> audios;

    public Episodio() {
        super();
    }

    public Episodio(final Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(final long id) {
        this.id = id;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(final Date fecha) {
        this.fecha = fecha;
    }

    public String getMedicamentos() {
        return medicamentos;
    }

    public void setMedicamentos(final String medicamentos) {
        this.medicamentos = medicamentos;
    }

    public String getNivelDolor() {
        return nivelDolor;
    }

    public void setNivelDolor(final String nivelDolor) {
        this.nivelDolor = nivelDolor;
    }

    public String getPatronesSueno() {
        return patronesSueno;
    }

    public void setPatronesSueno(final String patronesSueno) {
        this.patronesSueno = patronesSueno;
    }

    public String getActividades() {
        return actividades;
    }

    public void setActividades(final String actividades) {
        this.actividades = actividades;
    }

    @XmlTransient
    public List<Diagnostico> getDiagnosticos() {
        return diagnosticos;
    }

    public void setDiagnosticos(final List<Diagnostico> diagnosticos) {
        this.diagnosticos = diagnosticos;
    }

    public Paciente getPaciente() {
        return paciente;
    }

    public void setPaciente(final Paciente paciente) {
        this.paciente = paciente;
    }

    public List<AudioEpisodio> getAudios() {
        return audios;
    }

    public void setAudios(final List<AudioEpisodio> audios) {
        this.audios = audios;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(final Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Episodio)) {
            return false;
        }
        final Episodio other = (Episodio) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.ecos.controller.persistence.entities.Episodios[ id=" + id + " ]";
    }
}
