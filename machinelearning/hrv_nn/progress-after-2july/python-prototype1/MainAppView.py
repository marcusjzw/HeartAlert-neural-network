#!/usr/bin/env python 
# -*- coding: utf-8 -*-

import Tkinter as tk
import webbrowser 

class MainAppView(tk.Frame):
    """Encapsulates of all the GUI logic.

   

    Attributes:
        master: where to open the Frame, by deafult root window
        title: Main Label
     
        one: Button 
        two: Button 
        three: Button 

    """


    
    def start_gui(self,ok = True):
        """Starts the GUI, if everything ok , to change

        """
        
        if ok:
            self.mainloop()
        else:
            self.master.destroy()

    def createWidgets(self):
        """Create the set of initial widgets.

     

        """
        
        # Create the label

        self.title = tk.Label(
                self, text = "Detecting Onset of Ventricular Tachycardia")
        self.title.grid(
            row=0, column=0,columnspan=4, sticky = tk.E+tk.W )

      
        # Create the button

        self.one = tk.Button(self)
        self.one["text"] = "Send to Neural Network"
        self.one.grid(row=1, column=0)




    def __init__(self, master=None):
        tk.Frame.__init__(self, master)
        self.grid()
        # option is needed to put the main label in the window
        self.createWidgets()

        
    


