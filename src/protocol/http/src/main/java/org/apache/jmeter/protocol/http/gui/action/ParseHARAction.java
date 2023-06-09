/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to you under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.jmeter.protocol.http.gui.action;
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.HashSet;
import java.util.Set;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.MenuElement;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import org.apache.jmeter.gui.GuiPackage;
import org.apache.jmeter.gui.action.AbstractAction;
import org.apache.jmeter.gui.action.ActionNames;
import org.apache.jmeter.gui.action.ActionRouter;
import org.apache.jmeter.gui.action.Command;
import org.apache.jmeter.gui.plugin.MenuCreator;
import org.apache.jmeter.gui.util.EscapeDialog;
import org.apache.jmeter.gui.util.FilePanel;
import org.apache.jmeter.util.JMeterUtils;
import org.apache.jorphan.gui.ComponentUtil;
import org.apache.jorphan.gui.JMeterUIDefaults;

import com.google.auto.service.AutoService;

import static org.apache.jmeter.gui.action.ActionNames.IMPORT_HAR;

/*
import de.sstoehr.harreader.HarReader;
import de.sstoehr.harreader.HarReaderException;
import de.sstoehr.harreader.model.Har;
import de.sstoehr.harreader.model.HarEntry;
*/

@AutoService({
        Command.class,
        MenuCreator.class
})
public class ParseHARAction extends AbstractAction implements MenuCreator, ActionListener { // NOSONAR

    //private static final Logger LOGGER = LoggerFactory.getLogger(ParseCurlCommandAction.class);
    private static final Set<String> commands = new HashSet<>();

    private JLabel statusText;
    private static final String CREATE_REQUEST = "CREATE_REQUEST";
    private FilePanel filePanel = null;
    static {
        commands.add(IMPORT_HAR);
    }

    @Override
    public void doAction(ActionEvent e) {
        showInputDialog(e);
    }

    /**
     * Show popup where user can import HAR file
     *
     * @param event {@link ActionEvent}
     */
    private void showInputDialog(ActionEvent event) {
        EscapeDialog messageDialog = new EscapeDialog(
                getParentFrame(event),
                JMeterUtils.getResString("har_import"), //$NON-NLS-1$
                false);
        Container contentPane = messageDialog.getContentPane();
        contentPane.setLayout(new BorderLayout());
        statusText = new JLabel("",JLabel.CENTER);
        statusText.setForeground(UIManager.getColor(JMeterUIDefaults.LABEL_ERROR_FOREGROUND));
        contentPane.add(statusText, BorderLayout.NORTH);
        JPanel optionPanel = new JPanel(new BorderLayout(3, 1));
        filePanel = new FilePanel(JMeterUtils.getResString("har_import_from_file")); // $NON-NLS-1$
        optionPanel.add(filePanel,BorderLayout.CENTER);
        JButton button = new JButton(JMeterUtils.getResString("har_create_request"));
        button.setActionCommand(CREATE_REQUEST);
        button.addActionListener(this);
        button.setPreferredSize(new Dimension(50, 50));
        optionPanel.add(button,BorderLayout.SOUTH);
        contentPane.add(optionPanel, BorderLayout.SOUTH);
        messageDialog.pack();
        ComponentUtil.centerComponentInComponent(GuiPackage.getInstance().getMainFrame(), messageDialog);
        SwingUtilities.invokeLater(() -> messageDialog.setVisible(true));
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        //HarReader harReader = new HarReader();
        //Har har = harReader.readFromFile(filePanel.getFilename(), HarReaderMode.LAX);
        //List<HarEntry> entries = har.getLog().getEntries();
        //for (HarEntry entry : entries) {
            //System.out.println("Request URL: " + entry.getRequest().getUrl());
            //System.out.println("Response Status: " + entry.getResponse().getStatus());
            System.out.println("-----------------------------------");
        //s}

    }

    @Override
    public Set<String> getActionNames() {
        return commands;
    }

    @Override
    public JMenuItem[] getMenuItemsAtLocation(MENU_LOCATION location) {
        if (location == MENU_LOCATION.TOOLS) {
            // Use the action name as resource key because the action name is used by JMeterMenuBar too when changing languages.
            JMenuItem menuItemIH = new JMenuItem(JMeterUtils.getResString(IMPORT_HAR), KeyEvent.VK_UNDEFINED);
            menuItemIH.setName(IMPORT_HAR);
            menuItemIH.setActionCommand(IMPORT_HAR);
            menuItemIH.setAccelerator(null);
            menuItemIH.addActionListener(ActionRouter.getInstance());
            return new JMenuItem[] { menuItemIH };
        }
        return new JMenuItem[0];
    }

    @Override
    public JMenu[] getTopLevelMenus() {
        return new JMenu[0];
    }

    @Override
    public boolean localeChanged(MenuElement menu) {
        return false;
    }

    @Override
    public void localeChanged() {
        // NOOP
    }
}
