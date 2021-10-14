package aula.pdm.livros.controller

import aula.pdm.livros.MainActivity
import aula.pdm.livros.model.Livro
import aula.pdm.livros.model.LivroDao
import aula.pdm.livros.model.LivroSqlite

class LivroController(mainActivity: MainActivity) {
    private val livroDao: LivroDao = LivroSqlite(mainActivity)

    fun inserirLivro(livro: Livro) = livroDao.criarLivro(livro)
    fun buscarLivro(titulo: String) = livroDao.recupararLivro(titulo)
    fun buscarLivros() = livroDao.recuperarLivros()
    fun modificarLivro(livro: Livro) = livroDao.atualizarLivro(livro)
    fun apagarLivro(titulo: String) = livroDao.removerLivro(titulo)
}