package io.github.marattim.raif_api_guide;

/**
 * Предложение по исправлению ошибки
 * <p/>
 * <ul>
 *     <li>Если нужно вставить текст, то start=end=позиции вставки, а replace равен вставляемому тексту</li>
 *     <li>Если нужно заменить текст, то start и end указывают на начало и конец заменяемого текста, а replace на
 *     новый текст</li>
 *     <li>Если нужно удалить текст, то start и end указывают на начало и конец удаляемого текста, а replace равен
 *     пустой строке</li>
 * </ul>
 */
public interface Suggestion {
    /**
     * Часть, которую нужно исправить
     */
    Selection selection();

    /**
     * Новый текст
     */
    String replacement();

}
