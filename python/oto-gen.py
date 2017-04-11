'''
prerequisites to run
- python3 and tkinter installed
- have index.csv in the same folder
- have replace.csv in the same folder, if you plan to replace aliases

things this can do
- generate aliases arpasing-style from an index.csv
- add suffixes
- user settable oto base values
- optional start/end aliases
- find+replace aliases after generation (user settable)
- number or delete duplicate aliases
- has an ugly gui
- has all strings at beginning, for translators
- has an esperanto translation, just because

things this will do
- different oto base values based on phonemes (user settable)
- have a nicer gui
- work as a standalone application on all computers

things this won't do
- fancy audio analysis timing adjustment
'''

# English text
window_title = "OTO Generator"

suffix_text = "Suffix"
offset_text = "Initial offset"
offset_increment_text = "Offset increment per alias"
consonant_text = "Consonant"
cutoff_text = "Cutoff"
preutterance_text = "Preutterance"
overlap_text = "Overlap"

startend_alias_text = "String start/end aliases (ex. [- k])"
startend_alias_yes_text = "Include"
startend_alias_no_text = "Don't include"

replace_text = "Replace aliases based on replace.csv"
replace_yes_text = "Replace"
replace_no_text = "Don't replace"

duplicate_text = "Handling duplicate aliases"
duplicate_number_text = "Number duplicates"
duplicate_delete_text = "Delete duplicates"
duplicate_no_action_text = "Do nothing"

generate_button_text = "Generate OTO"

import tkinter as tk

# A single line of an oto.ini
class OTOline:
	__filename = ''
	__alias = ''
	__offset = 0
	__consonant = 0
	__cutoff = 0
	__preutterance = 0
	__overlap = 0
	
	def __init__(self, fn, a=None, of=None, co=None, cu=None, p=None, ov=None):
		if a is None and of is None and co is None and cu is None and p is None and ov is None:
			start = 0
			end = fn.find("=")
			self.__filename = fn[start:end]
			start = end+1
			end = fn.find(",",start)
			self.__alias = fn[start:end]
			start = end+1
			end = fn.find(",",start)
			self.__offset = int(fn[start:end])
			start = end+1
			end = fn.find(",",start)
			self.__consonant = int(fn[start:end])
			start = end+1
			end = fn.find(",",start)
			self.__cutoff = int(fn[start:end])
			start = end+1
			end = fn.find(",",start)
			self.__preutterance = int(fn[start:end])
			start = end+1
			self.__overlap = int(fn[start:])
		else:
			self.__filename = fn
			self.__alias = a
			self.__offset = of
			self.__consonant = co
			self.__cutoff = cu
			self.__preutterance = p
			self.__overlap = ov
	
	def set_filename(self, fn):
		self.__filename = fn	
	def get_filename(self):
		return self.__filename
	
	def set_alias(self, a):
		self.__alias = a	
	def get_alias(self):
		return self.__alias
	
	def set_offset(self, of):
		self.__offset = of
	def add_offset(self, add):
		self.__offset += add
	def get_offset(self):
		return self.__offset
	
	def set_consonant(self, co):
		self.__consonant = co	
	def get_consonant(self):
		return self.__consonant
	
	def set_cutoff(self, cu):
		self.__cutoff = cu	
	def get_cutoff(self):
		return self.__cutoff
	
	def set_preutterance(self, p):
		self.__preutterance = p	
	def get_preutterance(self):
		return self.__preutterance
	
	def set_overlap(self, ov):
		self.__overlap = ov	
	def get_overlap(self):
		return self.__overlap
	
	def toString(self):
		return self.__filename + "=" + self.__alias + "," + str(self.__offset) + "," + str(self.__consonant) + "," + str(self.__cutoff) + "," + str(self.__preutterance) + "," + str(self.__overlap)

# A single line of the index.csv
class IndexLine:
	__filename = ''
	__phonemes = []
	
	def __init__(self, line):
		self.__filename = line[:line.find(",")]
		self.__phonemes = line[line.find(",")+1:].split("_")
	
	def get_filename(self):
		return self.__filename
	
	def get_phonemes(self):
		return self.__phonemes
	
	def toString(self):
		return "The phonemes of " + self.__filename + " are " + str(self.__phonemes)

# window application and all the Guts of the program
class Application(tk.Frame):
	def __init__(self, master=None):
		tk.Frame.__init__(self, master)
		self.grid()
		self.create_widgets()
	
	def create_widgets(self):
		# Getting user input
		self.suffix_label = tk.Label(self, text=suffix_text)
		self.suffix_label.grid(column=0, row=0)
		self.suffix = tk.Entry(self)
		self.suffix.grid(column=1, row=0)
        
		self.offset_label = tk.Label(self, text=offset_text)
		self.offset_label.grid(column=0, row=1)
		self.offset = tk.Entry(self)
		self.offset.grid(column=1, row=1)
		
		self.offset_inc_label = tk.Label(self, text=offset_increment_text)
		self.offset_inc_label.grid(column=0, row=2)
		self.offset_inc = tk.Entry(self)
		self.offset_inc.grid(column=1, row=2)
        
		self.cons_label = tk.Label(self, text=consonant_text)
		self.cons_label.grid(column=0, row=3)
		self.cons = tk.Entry(self)
		self.cons.grid(column=1, row=3)
		
		self.cutoff_label = tk.Label(self, text=cutoff_text)
		self.cutoff_label.grid(column=0, row=4)
		self.cutoff = tk.Entry(self)
		self.cutoff.grid(column=1, row=4)
		
		self.preutt_label = tk.Label(self, text=preutterance_text)
		self.preutt_label.grid(column=0, row=5)
		self.preutt = tk.Entry(self)
		self.preutt.grid(column=1, row=5)
		
		self.overlap_label = tk.Label(self, text=overlap_text)
		self.overlap_label.grid(column=0, row=6)
		self.overlap = tk.Entry(self)
		self.overlap.grid(column=1, row=6)
		
		self.startend_choice = tk.StringVar()
		self.startend_label = tk.Label(self, text=startend_alias_text)
		self.startend_label.grid(row=0,column=2)
		self.startend_yes = tk.Radiobutton(self, value="y", variable=self.startend_choice,text=startend_alias_yes_text,anchor=tk.W)
		self.startend_yes.grid(row=1,column=2)
		self.startend_no = tk.Radiobutton(self, value="n", variable=self.startend_choice,text=startend_alias_no_text,anchor=tk.W)
		self.startend_no.grid(row=2,column=2)
		
		self.replace_choice = tk.StringVar()
		self.replace_label = tk.Label(self, text=replace_text)
		self.replace_label.grid(row=3,column=2)
		self.replace_yes = tk.Radiobutton(self, value="y", variable=self.replace_choice,text=replace_yes_text,anchor=tk.W)
		self.replace_yes.grid(row=4,column=2)
		self.replace_no = tk.Radiobutton(self, value="n", variable=self.replace_choice,text=replace_no_text,anchor=tk.W)
		self.replace_no.grid(row=5,column=2)
		
		self.duplic_choice = tk.StringVar()
		self.duplic_label = tk.Label(self, text=duplicate_text)
		self.duplic_label.grid(row=6,column=2)
		self.duplic_number = tk.Radiobutton(self, value="number", variable=self.duplic_choice,text=duplicate_number_text,anchor=tk.W)
		self.duplic_number.grid(row=7,column=2)
		self.duplic_delete = tk.Radiobutton(self, value="delete", variable=self.duplic_choice,text=duplicate_delete_text,anchor=tk.W)
		self.duplic_delete.grid(row=8,column=2)
		self.duplic_nothing = tk.Radiobutton(self, value="nothing", variable=self.duplic_choice,text=duplicate_no_action_text,anchor=tk.W)
		self.duplic_nothing.grid(row=9,column=2)
		
		self.generate_button = tk.Button(self, text=generate_button_text,command=self.generate_oto)
		self.generate_button.grid(column=0, row=8,columnspan=2)

	def generate_oto(self):
		# Changing data from widgets into plain variables
		user_suf = self.suffix.get()
		user_of = int(self.offset.get())
		user_of_inc = int(self.offset_inc.get())
		user_co = int(self.cons.get())
		user_cu = int(self.cutoff.get())
		user_p = int(self.preutt.get())
		user_ov = int(self.overlap.get())
		start_end = self.startend_choice.get()
		replace_switch = self.replace_choice.get()
		dup_option = self.duplic_choice.get()
		
		# Importing the index.csv and making a list of IndexLine objects
		index_raw = open("csv/index.csv","r+")
		index_file = (index_raw.read()).split("\n")
		index_raw.close()
		del index_file[len(index_file)-1]
		index_lines = []
		for line in index_file:
			index_lines.append(IndexLine(line))

		# Generating the aliases
		# print("Generating",end='')
		oto_file = open("result/oto.ini", "wb")
		oto_lines = []
		for line in index_lines:
			oto = OTOline(line.get_filename(),user_suf, user_of, user_co, user_cu, user_p, user_ov)
			if (start_end == 'y'):
				oto.set_alias("- " + str((line.get_phonemes())[0]) + user_suf)
				oto_file.write(bytes(oto.toString()+"\n", 'UTF-8'))
				oto_lines.append(OTOline(oto.toString()))
				oto.add_offset(user_of_inc)
			for i in range(0,len(line.get_phonemes())-1):
				oto.set_alias(str((line.get_phonemes())[i]) + " " + str((line.get_phonemes())[i+1]) + user_suf)
				oto_file.write(bytes(oto.toString()+"\n", 'UTF-8'))
				oto_lines.append(OTOline(oto.toString()))
				oto.add_offset(user_of_inc)
			if (start_end == 'y'):
				oto.set_alias(str((line.get_phonemes())[len(line.get_phonemes())-1]) + " -" + user_suf)
				oto_file.write(bytes(oto.toString()+"\n", 'UTF-8'))
				oto_lines.append(OTOline(oto.toString()))
			# print(".",end="")
		oto_file.close()

		# Replacing aliases based on replace.csv
		if (replace_switch == 'y'):
			# print("\nReplacing",end="")
			import csv
			reader = csv.reader(open('csv/replace.csv', 'r'))
			replace_dict = dict(reader)
			oto_lines_update = []
			oto_file = open("result/oto.ini", "wb")
			for line in oto_lines:
				alias = (line.get_alias())[:len(line.get_alias())-len(user_suf)]
				if alias in replace_dict:
					line.set_alias(replace_dict.get(alias) + user_suf)
					# print(".",end="")
				oto_file.write(bytes(line.toString()+"\n", 'UTF-8'))
				oto_lines_update.append(OTOline(line.toString()))
			oto_file.close()
			oto_lines = oto_lines_update

		# Number or delete duplicate aliases
		if (dup_option == 'number' or dup_option == 'delete'):
			'''if (dup_option == 'number'):
				print("\nNumbering duplicates",end="")
			else:
				print("\nDeleting duplicates",end="")'''
			oto_file = open("result/oto.ini", "wb")
			counter_dict = {}
			for line in oto_lines:
				alias = (line.get_alias())[:len(line.get_alias())-len(user_suf)]
				if alias in counter_dict:
					counter_dict[alias] += 1
					line.set_alias(alias + str(counter_dict[alias]) + user_suf)
					if (dup_option == 'number'):
						oto_file.write(bytes(line.toString()+"\n", 'UTF-8'))
					# print(".",end="")
				else:
					counter_dict[alias] = 0
					oto_file.write(bytes(line.toString()+"\n", "UTF-8"))
			oto_file.close()

		# print("\nDone.")
		quit()

app = Application()
app.master.title(window_title)
app.mainloop()
