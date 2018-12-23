package Utils;

import java.util.ArrayList;
import java.util.List;

import org.telegram.telegrambots.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.InlineKeyboardButton;

public class InlineKeyboardsBuilder {

	public InlineKeyboardMarkup composeInlineKeyboard_Citta() {

		InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();
		List<List<InlineKeyboardButton>> rowsInline = new ArrayList<List<InlineKeyboardButton>>();
		List<InlineKeyboardButton> rowInline = null;
		List<String> citta_List = Jsoup_HTML.getCitta();

		for (int i = 0; i < citta_List.size(); i++) {

			rowInline = new ArrayList<InlineKeyboardButton>();
			rowInline.add(new InlineKeyboardButton().setText(citta_List.get(i)).setCallbackData(citta_List.get(i)));

			rowsInline.add(rowInline);

		}

		markupInline.setKeyboard(rowsInline);

		return markupInline;

	}

	public InlineKeyboardMarkup composeInlineKeyboard_Prova2() {
		InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();
		List<List<InlineKeyboardButton>> rowsInline = new ArrayList<List<InlineKeyboardButton>>();
		List<InlineKeyboardButton> rowInline = new ArrayList<InlineKeyboardButton>();

		rowInline.add(new InlineKeyboardButton().setText("tom").setCallbackData("tom"));
		rowInline.add(new InlineKeyboardButton().setText("ben").setCallbackData("ben"));

		rowsInline.add(rowInline);

		markupInline.setKeyboard(rowsInline);

		return markupInline;

	}

}
