package aula.pdm.livros.adapter

import android.content.Context
import android.content.Context.LAYOUT_INFLATER_SERVICE
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import aula.pdm.livros.databinding.LayoutLivroBinding
import aula.pdm.livros.model.Livro

class LivrosAdapter(
    val contexto: Context,
    val leiaute: Int,
    val listaLivros: MutableList<Livro>
    ): ArrayAdapter<Livro>(contexto, leiaute, listaLivros) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val livroLayoutView: View
        if(convertView != null){
            //célula reciclada
            livroLayoutView = convertView
        } else{
            //inflar uma célula nova
            val layoutLivroBinding = LayoutLivroBinding.inflate(
                contexto.getSystemService(LAYOUT_INFLATER_SERVICE) as LayoutInflater
            )
            with (layoutLivroBinding){
                livroLayoutView = layoutLivroBinding.root
                livroLayoutView.tag = LivroLayoutHolder(tituloTv, primeiroAutorTv, editoraTv)
            }
        }

        //Atualizar os dados da célula, seja nova, seja reciclada
        val livro = listaLivros[position]

        with (livroLayoutView.tag as LivroLayoutHolder) {
            tituloTv.text = livro.titulo
            primeiroAutorTv.text = livro.primeiroAutor
            editoraTv.text = livro.editora
        }
        return livroLayoutView
    }

    private data class LivroLayoutHolder(
        val tituloTv: TextView,
        val primeiroAutorTv: TextView,
        val editoraTv: TextView
    )
}