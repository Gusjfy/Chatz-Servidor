/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

/**
 *
 * @author Leonardo Steinke
 */
public class UsuarioContatos {

    private int idUsuario;
    private int idContato;

    public UsuarioContatos(int idUsuario, int idContato) {
        this.idUsuario = idUsuario;
        this.idContato = idContato;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public int getIdContato() {
        return idContato;
    }

}
