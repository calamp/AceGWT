// Copyright (c) 2011-2012, David H. Hovemeyer <david.hovemeyer@gmail.com>
// 
// Permission is hereby granted, free of charge, to any person obtaining a copy
// of this software and associated documentation files (the "Software"), to deal
// in the Software without restriction, including without limitation the rights
// to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
// copies of the Software, and to permit persons to whom the Software is
// furnished to do so, subject to the following conditions:
// 
// The above copyright notice and this permission notice shall be included in
// all copies or substantial portions of the Software.
// 
// THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
// IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
// FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
// AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
// LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
// OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
// THE SOFTWARE.

package edu.ycp.cs.dh.acegwt.client.ace;


import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.InlineLabel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
   
public class AceGWT implements EntryPoint {
    /**
     * The message displayed to the user when the server cannot be reached or
     * returns an error.
     */
    private AceEditor editor1;
  //  private AceEditor editor2;
    private InlineLabel rowColLabel;
    /**
     * This is the entry point method.
     */
    public void onModuleLoad() {   

        editor1 = new AceEditor(true);
        editor1.setWidth("800px");
        editor1.setHeight("300px");
        
        // create second AceEditor widget
     //   editor2 = new AceEditor(true);
     //   editor2.setWidth("800px");
     //   editor2.setHeight("300px");
        
        // build the UI
        buildUI();
        
        // start the first editor and set its theme and mode
        editor1.startEditor(); // must be called before calling setTheme/setMode/etc.
        editor1.setTheme(AceEditorTheme.ECLIPSE);
        editor1.setMode(AceEditorMode.CLIDE);
        
        // use cursor position change events to keep a label updated
        // with the current row/col
        editor1.addOnCursorPositionChangeHandler(new AceEditorCallback() {
          
                public void invokeAceCallback(JavaScriptObject obj) {
                        updateEditor1CursorPosition();
                }
        });
        updateEditor1CursorPosition(); // initial update
        
        // start the second editor and set its theme and mode
      //  editor2.startEditor();
      // editor2.setTheme(AceEditorTheme.TWILIGHT);
      // editor2.setMode(AceEditorMode.XML);

        RootPanel.get("editor1").add(editor1);
     //   RootPanel.get("editor2").add(editor2);
        

    }
    /**
     * This method builds the UI.
     * It creates UI widgets that exercise most/all of the AceEditor methods,
     * so it's a bit of a kitchen sink.
     */
    private void buildUI() {
            VerticalPanel mainPanel = new VerticalPanel();
            mainPanel.setWidth("100%");
            
            mainPanel.add(new Label("Label above!"));
            
            mainPanel.add(editor1);
            
            // Label to display current row/column
            rowColLabel = new InlineLabel("");
            mainPanel.add(rowColLabel);
            
            // Create some buttons for testing various editor APIs
            HorizontalPanel buttonPanel = new HorizontalPanel();
            
            // Add button to insert text at current cursor position
            Button insertTextButton = new Button("Insert");
            insertTextButton.addClickHandler(new ClickHandler() {
           
                    public void onClick(ClickEvent event) {
                            //Window.alert("Cursor at: " + editor1.getCursorPosition());
                            editor1.insertAtCursor("inserted text!");
                    }
            });
            buttonPanel.add(insertTextButton);
            
            // Add check box to enable/disable soft tabs
            final CheckBox softTabsBox = new CheckBox("Soft tabs");
            softTabsBox.setValue(true); // I think soft tabs is the default
            softTabsBox.addClickHandler(new ClickHandler() {
                 
                    public void onClick(ClickEvent event) {
                            editor1.setUseSoftTabs(softTabsBox.getValue());
                    }
            });
            buttonPanel.add(softTabsBox);
            
            // add text box and button to set tab size
            final TextBox tabSizeTextBox = new TextBox();
            tabSizeTextBox.setWidth("4em");
            Button setTabSizeButton = new Button("Set tab size");
            setTabSizeButton.addClickHandler(new ClickHandler() {
                
                    public void onClick(ClickEvent event) {
                            editor1.setTabSize(Integer.parseInt(tabSizeTextBox.getText()));
                    }
            });
            buttonPanel.add(new InlineLabel("Tab size: "));
            buttonPanel.add(tabSizeTextBox);
            buttonPanel.add(setTabSizeButton);
            
            // add text box and button to go to a given line
            final TextBox gotoLineTextBox = new TextBox();
            gotoLineTextBox.setWidth("4em");
            Button gotoLineButton = new Button("Go to line");
            gotoLineButton.addClickHandler(new ClickHandler() {
                    
                    public void onClick(ClickEvent event) {
                            editor1.gotoLine(Integer.parseInt(gotoLineTextBox.getText()));
                    }
            });
            buttonPanel.add(new InlineLabel("Go to line: "));
            buttonPanel.add(gotoLineTextBox);
            buttonPanel.add(gotoLineButton);
            
            // checkbox to set whether or not horizontal scrollbar is always visible
            final CheckBox hScrollBarAlwaysVisibleBox = new CheckBox("H scrollbar: ");
            hScrollBarAlwaysVisibleBox.setValue(true);
            hScrollBarAlwaysVisibleBox.addClickHandler(new ClickHandler() {
                    
                    public void onClick(ClickEvent event) {
                            editor1.setHScrollBarAlwaysVisible(hScrollBarAlwaysVisibleBox.getValue());
                    }
            });
            buttonPanel.add(hScrollBarAlwaysVisibleBox);
            
            // checkbox to show/hide gutter
            final CheckBox showGutterBox = new CheckBox("Show gutter: ");
            showGutterBox.setValue(true);
            showGutterBox.addClickHandler(new ClickHandler() {
                    
                    public void onClick(ClickEvent event) {
                            editor1.setShowGutter(showGutterBox.getValue());
                    }
            });
            buttonPanel.add(showGutterBox);
            
            // checkbox to set/unset readonly mode
            final CheckBox readOnlyBox = new CheckBox("Read only: ");
            readOnlyBox.setValue(false);
            readOnlyBox.addClickHandler(new ClickHandler() {
                    
                    public void onClick(ClickEvent event) {
                            editor1.setReadOnly(readOnlyBox.getValue());
                    }
            });
            buttonPanel.add(readOnlyBox);
            
            // checkbox to show/hide print margin
            final CheckBox showPrintMarginBox = new CheckBox("Show print margin: ");
            showPrintMarginBox.setValue(true);
            showPrintMarginBox.addClickHandler(new ClickHandler() {
                    
                    public void onClick(ClickEvent event) {
                            editor1.setShowPrintMargin(showPrintMarginBox.getValue());
                    }
            });
            buttonPanel.add(showPrintMarginBox);
            
            mainPanel.add(buttonPanel);
            
        //    mainPanel.add(editor2);
            mainPanel.add(new Label("Label below!"));
            
            RootPanel.get().add(mainPanel);
    }

    private void updateEditor1CursorPosition() {
            rowColLabel.setText(editor1.getCursorPosition().toString());
    }
}
