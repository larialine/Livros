package aula.pdm.livros.model

interface LivroDao {
    fun criarLivro(livro: Livro): Long
    fun recupararLivro(titulo: String): Livro
    fun recuperarLivros(): MutableList<Livro>
    fun atualizarLivro(livro: Livro): Int       //retorna quantidade de linhas alteradas
    fun removerLivro(titulo: String): Int       //retorna quantidade de linhas excluídas
}