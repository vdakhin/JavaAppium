package tests;

import lib.CoreTestCase;
import lib.Platform;
import lib.ui.*;
import lib.ui.factories.ArticlePageObjectFactory;
import lib.ui.factories.MyListsPageObjectFactory;
import lib.ui.factories.NavigationUIFactory;
import lib.ui.factories.SearchPageObjectFactory;
import org.junit.Test;

import java.util.concurrent.TimeUnit;

public class MyListsTests extends CoreTestCase
{
    private static String
            name_of_folder_for_testSaveArticleToMyList = "Learning programming",
            name_of_folder_for_testSaveTwoArticlesToMyList = "List with two articles";

    private static final String
            login = "Shikarno4000",
            password = "Shikarno4001";

    @Test
    public void testSaveArticleToMyList()
    {
        SearchPageObject SearchPageObject = SearchPageObjectFactory.get(driver);

        SearchPageObject.initSearchInput();
        SearchPageObject.typeSearchLine("Java");
        SearchPageObject.clickByArticleWithSubstring("bject-oriented programming language");

        ArticlePageObject ArticlePageObject = ArticlePageObjectFactory.get(driver);
        ArticlePageObject.waitForTitleElement();
        String first_article_title = ArticlePageObject.getArticleTitle();

        driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);

        if(Platform.getInstance().isAndroid()) {
            ArticlePageObject.addArticleToNewList(name_of_folder_for_testSaveArticleToMyList);
        } else {
            ArticlePageObject.addArticlesToMySaved();
        }

        if (Platform.getInstance().isMW()) {
            AuthorizationPageObject Auth = new AuthorizationPageObject(driver);

            Auth.clickAuthButton();
            driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
            Auth.enterLoginData(login, password);
            Auth.submitForm();
            ArticlePageObject.waitForTitleElement();

            assertEquals(
                    "We are not on the same page after login",
                    first_article_title,
                    ArticlePageObject.getArticleTitle()
            );

            ArticlePageObject.addArticlesToMySaved();
        }

        if(Platform.getInstance().isIOS()) {
        ArticlePageObject.closeSyncYourSavedArticlesPopUp();
        }

        ArticlePageObject.closeArticle();

        NavigationUI NavigationUI = NavigationUIFactory.get(driver);
        NavigationUI.openNavigation();
        NavigationUI.clickMyLists();

        MyListsPageObject MyListPageObject = MyListsPageObjectFactory.get(driver);

        if(Platform.getInstance().isAndroid()) {
            MyListPageObject.openFolderByName(name_of_folder_for_testSaveArticleToMyList);
        }

        MyListPageObject.swipeByArticleToDelete(first_article_title);
    }

    @Test
    public void testSaveTwoArticlesToMyList()
    {
        SearchPageObject SearchPageObject = SearchPageObjectFactory.get(driver);

        SearchPageObject.initSearchInput();
        SearchPageObject.typeSearchLine("Java");
        SearchPageObject.clickByArticleWithSubstring("bject-oriented programming language");

        ArticlePageObject ArticlePageObject = ArticlePageObjectFactory.get(driver);
        ArticlePageObject.waitForTitleElement();
        String first_article_title = ArticlePageObject.getArticleTitle();

        driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);

        if(Platform.getInstance().isAndroid()) {
            ArticlePageObject.addArticleToNewList(name_of_folder_for_testSaveTwoArticlesToMyList);
        } else {
            ArticlePageObject.addArticlesToMySaved();
        }

        if (Platform.getInstance().isMW()) {
            AuthorizationPageObject Auth = new AuthorizationPageObject(driver);

            Auth.clickAuthButton();
            Auth.enterLoginData(login, password);
            Auth.submitForm();
            ArticlePageObject.waitForTitleElement();

            assertEquals(
                    "We are not on the same page after login",
                    first_article_title,
                    ArticlePageObject.getArticleTitle()
            );

            ArticlePageObject.addArticlesToMySaved();
        }

        if(Platform.getInstance().isIOS()) {
            ArticlePageObject.closeSyncYourSavedArticlesPopUp();
        }

        ArticlePageObject.closeArticle();

        SearchPageObject.initSearchInput();

        if(Platform.getInstance().isAndroid() || Platform.getInstance().isMW()) {
            SearchPageObject.typeSearchLine("Java");
        }

        SearchPageObject.clickByArticleWithSubstring("avaScript");

        ArticlePageObject.waitForTitleElement();

        if(Platform.getInstance().isAndroid()) {
            ArticlePageObject.addArticleToNewList(name_of_folder_for_testSaveTwoArticlesToMyList);
        } else {
            ArticlePageObject.addArticlesToMySaved();
        }

        ArticlePageObject.closeArticle();

        NavigationUI NavigationUI = NavigationUIFactory.get(driver);
        NavigationUI.openNavigation();
        NavigationUI.clickMyLists();

        MyListsPageObject MyListPageObject = MyListsPageObjectFactory.get(driver);

        if(Platform.getInstance().isAndroid()) {
            MyListPageObject.openFolderByName(name_of_folder_for_testSaveArticleToMyList);
        }

        driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
        MyListPageObject.swipeByArticleToDelete(first_article_title);
        String second_article_title_on_preview = MyListPageObject.getArticleTitle();
        driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
        MyListPageObject.clickArticleByTitle("JavaScript");
        driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
        String second_article_title_on_the_article_page = ArticlePageObject.getArticleTitle();

        assertEquals(
                "Unexpected title",
                second_article_title_on_preview,
                second_article_title_on_the_article_page
        );
    }
}