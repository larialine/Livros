package aula.pdm.livros.adapter

import android.view.*
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import aula.pdm.livros.R
import aula.pdm.livros.databinding.LayoutLivroBinding
import aula.pdm.livros.model.Livro
import aula.pdm.livros.onLivroClickListener

class LivrosRvAdapter(
    private val onLivroClickListener: onLivroClickListener,
    private val livrosList: MutableList<Livro>
): RecyclerView.Adapter<LivrosRvAdapter.LivroLayoutHolder>() {

    //Posição que será recuperada pelo menu de contexto
    var posicao: Int = -1

    //ViewHolder
    inner class LivroLayoutHolder(layoutLivroBinding: LayoutLivroBinding): RecyclerView.ViewHolder(layoutLivroBinding.root), View.OnCreateContextMenuListener{
        val tituloTv: TextView = layoutLivroBinding.tituloTv
        val primeiroAutorTv : TextView = layoutLivroBinding.primeiroAutorTv
        val editoraTv: TextView = layoutLivroBinding.editoraTv
        init {
            itemView.setOnCreateContextMenuListener(this)
        }

        override fun onCreateContextMenu(
            menu: ContextMenu?,
            view: View?,
            menuInfo: ContextMenu.ContextMenuInfo?
        ) {
            MenuInflater(view?.context).inflate(R.menu.context_menu_main, menu)
        }
    }


    // Quando uma nova cécular precisar ser criada
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LivroLayoutHolder {
        // Criar uma nova célular
        val layoutLivroBinding =  LayoutLivroBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        //Criar um viewHolder associado a nova célula
        val viewHolder: LivroLayoutHolder = LivroLayoutHolder(layoutLivroBinding)
        return viewHolder
    }

    // Quando necessário atualizar valores de uma cécula, seja uma célula nova ou antiga
    override fun onBindViewHolder(holder: LivroLayoutHolder, position: Int) {
        // Buscar o livro
        val livro = livrosList[position]

        // Atualizar os valores do viewHolder
        with(holder){
            tituloTv.text = livro.titulo
            primeiroAutorTv.text = livro.primeiroAutor
            editoraTv.text = livro.editora
            itemView.setOnClickListener {
                onLivroClickListener.onLivroClick(position)
            }
            itemView.setOnLongClickListener{
                posicao = position
                false
            }
        }
    }

    override fun getItemCount(): Int {
        return livrosList.size
    }
}