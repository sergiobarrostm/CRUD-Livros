package model;

/**
 *
 * @author Eliaquim, João, Laryssa, Sérgio
 */  
 public class Livro {
    private String nomeLivro, genero,nomeAutor, editora;
    private int codigo;
    
    public Livro() {
    }

    public Livro(int codigo, String nomeLivro, String genero, String nomeAutor, String editora) {
        setCodigo(codigo);
        setNomeLivro(nomeLivro);       
        setGenero(genero);
        setNomeAutor(nomeAutor);
        setEditora(editora);
    }

    public String getNomeLivro() {
        return nomeLivro;
    }

    public void setNomeLivro(String nomeLivro) {
        this.nomeLivro = nomeLivro;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public String getNomeAutor() {
        return nomeAutor;
    }

    public void setNomeAutor(String nomeAutor) {
        this.nomeAutor = nomeAutor;
    }

    public String getEditora() {
        return editora;
    }

    public void setEditora(String editora) {
        this.editora = editora;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }
}
