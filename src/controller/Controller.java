package controller;


import view.TelaEditar;
import view.TelaInicio;
import view.TelaCadastro;
import connection.ConnectionFactory;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.RowFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import model.Livro;

/**
 *
 * @author Eliaquim, João, Laryssa, Sérgio
 */
public class Controller {
    
    public static void Tela1(javax.swing.JFrame tela) {
        TelaInicio frame = new TelaInicio();
        frame.setVisible(true);
        tela.setVisible(false);
        frame.setLocationRelativeTo(null);
    }

    public static void Tela2(javax.swing.JFrame tela) {
        TelaCadastro frame = new TelaCadastro();
        frame.setVisible(true);
        tela.setVisible(false);
        frame.setLocationRelativeTo(null);
    }

    public static void Tela3(javax.swing.JFrame tela) {
        TelaEditar frame = new TelaEditar();
        frame.setVisible(true);
        tela.setVisible(false);
        frame.setLocationRelativeTo(null);
    }

    public void Cadastrar(Livro l) {

        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        try {
            stmt = (PreparedStatement) con.prepareStatement("INSERT INTO livros (nome,genero,autor,editora) VALUES(?,?,?,?)");
            stmt.setString(1, l.getNomeLivro());
            stmt.setString(2, l.getGenero());
            stmt.setString(3, l.getNomeAutor());
            stmt.setString(4, l.getEditora());

            stmt.executeUpdate();

            JOptionPane.showMessageDialog(null, "Livro cadastrado com sucesso!");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao salvar:" + ex);
        } finally {
            ConnectionFactory.closeConnection(con, stmt);
        }

    }

    public static void Verifica(Livro l) {
        Controller c = new Controller();

        if (l.getNomeLivro().trim().equals("") || l.getNomeAutor().trim().equals("") || l.getEditora().trim().equals("") 
                || l.getEditora().trim().equals("")) {
            
            JOptionPane.showMessageDialog(null, "Para efetuar o cadastro todos os campos precisam ser preenchidos!");
        
        }else {
            c.Cadastrar(l);
        }

    }

    public List<Livro> Listar() {

        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;

        List<Livro> livros = new ArrayList<>();

        try {
            stmt = con.prepareStatement("SELECT * FROM livros");
            rs = stmt.executeQuery();

            while (rs.next()) {
                Livro livro = new Livro();

                livro.setCodigo(rs.getInt("id"));
                livro.setNomeLivro(rs.getString("nome"));
                livro.setNomeAutor(rs.getString("autor"));
                livro.setEditora(rs.getString("editora"));
                livro.setGenero(rs.getString("genero"));
                livros.add(livro);
            }

        } catch (SQLException ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
        return livros;
    }

    public static void pesquisarContato(javax.swing.JTable tabela, javax.swing.JFormattedTextField pesquisa, DefaultTableModel modelo) {
        TableRowSorter<DefaultTableModel> tr = new TableRowSorter<DefaultTableModel>(modelo);
        tabela.setRowSorter(tr);
        tr.setRowFilter(RowFilter.regexFilter(pesquisa.getText()));
    }
    
    public static int posicao;
    
    public static void BotaoEditar(JTable tabela, JFrame tela){
        
        if (tabela.getSelectedRowCount() == 0){
            JOptionPane.showMessageDialog(null, "Selecione o livro que deseja editar!");
        } else {
            posicao = (int) tabela.getValueAt(tabela.getSelectedRow(), 0);
            Tela3(tela);
        }
    }
    
    public static void Atualizar(Livro l) {

        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        try {

            stmt = con.prepareStatement("UPDATE livros SET nome = ?,genero = ?,autor = ?,editora = ? WHERE id = ?");
            stmt.setString(1, l.getNomeLivro());
            stmt.setString(2, l.getGenero());
            stmt.setString(3, l.getNomeAutor());
            stmt.setString(4, l.getEditora());
            stmt.setInt(5, posicao);

            stmt.executeUpdate();

            JOptionPane.showMessageDialog(null, "Livro Atualizado com sucesso!");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao Atualizar:" + ex);
        } finally {
            ConnectionFactory.closeConnection(con, stmt);
        }
    }
    public static int posicao1;
    
    public static void BotaoDelete(javax.swing.JButton botao, JTable tabela){
        if (tabela.getSelectedRowCount() == 0){
            JOptionPane.showMessageDialog(null, "Selecione o livro que deseja excluir!");
        }else{
            DefaultTableModel modelo = (DefaultTableModel) tabela.getModel();
            posicao1 = (int) tabela.getValueAt(tabela.getSelectedRow(), 0);
        }
    }
    
    public static void Delete(Livro l) {

        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        try {
            
            int resposta = JOptionPane.showConfirmDialog(null, "Tem certeza que deseja excluir esse livro ?");

            if (resposta == JOptionPane.YES_OPTION) {
                stmt = con.prepareStatement("DELETE FROM livros  WHERE id = ?");
                stmt.setInt(1, posicao1);

                stmt.executeUpdate();
                
                JOptionPane.showMessageDialog(null, "Livro Excluido com sucesso!");
            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao Excluir:" + ex);
        } finally {
            ConnectionFactory.closeConnection(con, stmt);
        }
    }
}