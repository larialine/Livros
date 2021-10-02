package aula.pdm.livros

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.*
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import aula.pdm.livros.adapter.LivrosAdapter
import aula.pdm.livros.databinding.ActivityMainBinding
import aula.pdm.livros.model.Livro

class MainActivity : AppCompatActivity() {
    //conseguir passar parametros de uma tela para outra
    companion object Extras{
        //const em kotlin = static em java
        const val EXTRA_LIVRO = "EXTRA LIVRO"
        const val EXTRA_POSICAO = "EXTRA_POSICAO"
    }

    private val activityMainBinding: ActivityMainBinding by lazy{
        ActivityMainBinding.inflate(layoutInflater)
    }

    private lateinit var livroActivityResultLauncher: ActivityResultLauncher<Intent>
    private lateinit var editarLivroActivityResultLauncher: ActivityResultLauncher<Intent>

    // Data source
    private val livrosList: MutableList<Livro> = mutableListOf()

    // Adapter
   /* private val livrosAdapter: ArrayAdapter<String> by lazy{
        ArrayAdapter(this, android.R.layout.simple_list_item_1, livrosList.run{
            val livrosStringList = mutableListOf<String>()
            livrosList.forEach { livro -> livrosStringList.add(livro.toString()) }
            livrosStringList //retornando (return)
        })
    } */
    private val livrosAdapter: LivrosAdapter by lazy{
        LivrosAdapter(this, R.layout.layout_livro, livrosList)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(activityMainBinding.root)

        //Inicializando lista de livros
        inicializarLivrosList()

        // Associando Adapter ao ListView
        activityMainBinding.livrosLv.adapter = livrosAdapter

        registerForContextMenu(activityMainBinding.livrosLv)

        livroActivityResultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
            resultado ->
            if (resultado.resultCode == RESULT_OK){
                //recebendo o livro
                resultado.data?.getParcelableExtra<Livro>(EXTRA_LIVRO)?.apply {
                    //adicionando o livro no livroList e no Adapter
                    livrosList.add(this)
                    //livrosAdapter.add(this.toString()) //irá aparecer na última posição do listView
                    livrosAdapter.notifyDataSetChanged()
                }
            }
        }

        editarLivroActivityResultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
                resultado ->
            if (resultado.resultCode == RESULT_OK){
                val posicao = resultado.data?.getIntExtra(EXTRA_POSICAO, -1)
                 resultado.data?.getParcelableExtra<Livro>(EXTRA_LIVRO)?.apply {
                    if(posicao != null && posicao != -1){
                        livrosList[posicao] = this
                        livrosAdapter.notifyDataSetChanged()
                    }
                }
            }
        }

        activityMainBinding.livrosLv.setOnItemClickListener{_, _, posicao, _ ->
            val livro  = livrosList[posicao]
            val consultarLivroIntent = Intent(this, LivroActivity::class.java)
            consultarLivroIntent.putExtra(EXTRA_LIVRO, livro)
            startActivity(consultarLivroIntent)
        }

        activityMainBinding.adicionarLivroFab.setOnClickListener{
            livroActivityResultLauncher.launch(Intent(this, LivroActivity::class.java))
        }

    }

    private fun inicializarLivrosList(){
        for(indice in 1..10){
            livrosList.add(
                Livro(
                    "Título ${indice}",
                    "Isbn ${indice}",
                    "Primeiro autor: ${indice}",
                    "Editora ${indice}",
                    indice,
                    indice
                )
            )
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean{
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean = when(item.itemId) {
        R.id.adicionarLivroMi -> {
            livroActivityResultLauncher.launch(Intent(this, LivroActivity::class.java))
            true
        } else -> {
            false
        }
    }

    override fun onCreateContextMenu(
        menu: ContextMenu?,
        v: View?,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        super.onCreateContextMenu(menu, v, menuInfo)
        menuInflater.inflate(R.menu.context_menu_main, menu)
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        val posicao = (item.menuInfo as AdapterView.AdapterContextMenuInfo).position

        return when (item.itemId) {
            R.id.editarLivroMi -> {
                //Editar o livro
                val livro = livrosList[posicao]
                val editarLivroIntent = Intent(this, LivroActivity::class.java)
                editarLivroIntent.putExtra(EXTRA_LIVRO, livro)
                editarLivroIntent.putExtra(EXTRA_POSICAO, posicao)
                editarLivroActivityResultLauncher.launch(editarLivroIntent)
                true
            }
            R.id.removerLivroMi -> {
                //Remover o livro
                livrosList.removeAt(posicao)
                livrosAdapter.notifyDataSetChanged()
                true
            }
            else -> {
                false
            }
        }
    }
}