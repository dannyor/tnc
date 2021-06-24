package dnl.bible.json

import dnl.bible.api.BibleBook
import dnl.bible.api.*

class BibleImpl(val delegate: Bible) : dnl.bible.api.Bible {
    override fun getBook(book: BibleBook): dnl.bible.api.Book {
        return BookImpl(delegate.getBook(book))
    }
}

class BookImpl(val delegate: Book) : dnl.bible.api.Book {
    override fun getName(): String {
        return delegate.name
    }

    override fun getHebrewName(): String {
        return delegate.hebrewName
    }

    override fun getBookEnum(): BibleBook {
        return delegate.bookEnumVal
    }

    override fun getNumOfChapters(): Int {
        return delegate.chapters.size
    }

    override fun getChapter(index: Int): dnl.bible.api.Chapter {
        return ChapterImpl(this, index, delegate.chapters[index-1])
    }
}

class ChapterImpl(
    private val parentBook: dnl.bible.api.Book,
    private val chapterIndex: Int,
    private val delegate: Chapter
) : dnl.bible.api.Chapter {
    override fun getParent(): dnl.bible.api.Book {
        return parentBook
    }

    override fun getIndex(): Int {
        return chapterIndex
    }

    override fun getAllVerses(): List<Verse> {
        return getIterator().asSequence().toList()
    }

    override fun getAllVersesStrings(): List<String> {
        return delegate.verses
    }

    override fun getNumOfVerses():Int {
        return delegate.verses.size
    }

    override fun getVerse(index: Int): Verse {
        return VerseImpl(delegate.verses[index-1], this, VerseLocation(parentBook.getBookEnum(), chapterIndex, index))
    }
}

class VerseImpl(
    private val verseText: String,
    private val parentChapter: dnl.bible.api.Chapter,
    private val verseLocation: VerseLocation
) : Verse {
    override fun getParent(): dnl.bible.api.Chapter {
        return parentChapter
    }

    override fun getIndex(): Int {
        return verseLocation.verseIndex
    }

    override fun getWords(): List<String> {
        return verseText.split(' ')
    }

    override fun getText(): String {
        return verseText
    }

}