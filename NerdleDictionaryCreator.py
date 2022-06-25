import re

# nerdle candidate generator

digitlist = ['0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '+', '-', '*', '/','=']

def main():
	candidates = []
	# generate all possibilities (note that we exclude the equals sign and operators from first/last place)
	for d1 in range(10):
		for d2 in range(15):
			print("Progress", d1, "out of", 9, ";", d2, "out of", 14)
			for d3 in range(15):
				for d4 in range(15):
					for d5 in range(15):
						for d6 in range(15):
							for d7 in range(15):
								for d8 in range(10):
									candidate = ""
									candidate += digitlist[d1]
									candidate += digitlist[d2]
									candidate += digitlist[d3]
									candidate += digitlist[d4]
									candidate += digitlist[d5]
									candidate += digitlist[d6]
									candidate += digitlist[d7]
									candidate += digitlist[d8]
									# first, check for one equal sign
									if candidate.count("=") != 1:
										continue
									# next, check for operators
									if not ("+" in candidate or "-" in candidate or "*" in candidate or "/" in candidate):
										continue
									# next, check for adjacent operators
									if hasadjops(candidate):
										continue
									# next, check for operator after equals sign
									if hasopsafterequals(candidate):
										continue
									# finally, check for leading zeros
									if hasleadingzeros(candidate):
										continue
									# split into two parts and attempt to parse numerically
									parts = candidate.split("=")
									try: 
										a = eval(parts[0])
										b = eval(parts[1])
										if (a == b):
											candidates.append(candidate)
									except:
										pass
	output = open("nerdle.txt", "w")
	for c in candidates:
		output.write(c + "\n")
	output.close()

def hasadjops(candidate):
	for i in range(len(candidate)-1):
		if digitlist.index(candidate[i]) >= 10 and digitlist.index(candidate[i]) <= 14:
			if digitlist.index(candidate[i+1]) >= 10 and digitlist.index(candidate[i+1]) <= 14:
				return True
	return False

def hasopsafterequals(candidate):
	location = candidate.index("=")
	for i in range(location+1, len(candidate)):
		if digitlist.index(candidate[i]) >= 10:
			return True
	return False

def hasleadingzeros(candidate):
	elements = re.split("\*|\-|\/|\+|=", candidate)
	for element in elements:
		if len(element) > 1 and element[0] == '0':
			return True
	return False

main()
