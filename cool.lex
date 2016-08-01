/*
 *  The scanner definition for COOL.
 */

import java_cup.runtime.Symbol;

%%

%{

/*  Stuff enclosed in %{ %} is copied verbatim to the lexer class
 *  definition, all the extra variables/functions you want to use in the
 *  lexer actions should go here.  Don't remove or modify anything that
 *  was there initially.  */

    // Max size of string constants
    static int MAX_STR_CONST = 1025;

    // For assembling string constants
    StringBuffer string_buf = new StringBuffer();

    private int curr_lineno = 1;
    int comentario = 0;
    int get_curr_lineno() {
	return curr_lineno;
    }

    private AbstractSymbol filename;

    void set_filename(String fname) {
	filename = AbstractTable.stringtable.addString(fname);
    }

    AbstractSymbol curr_filename() {
	return filename;
    }
   
%}

%init{

/*  Stuff enclosed in %init{ %init} is copied verbatim to the lexer
 *  class constructor, all the extra initialization you want to do should
 *  go here.  Don't remove or modify anything that was there initially. */

    // empty for now
%init}

%eofval{

/*  Stuff enclosed in %eofval{ %eofval} specifies java code that is
 *  executed when end-of-file is reached.  If you use multiple lexical
 *  states and want to do something special if an EOF is encountered in
 *  one of those states, place your code in the switch statement.
 *  Ultimately, you should return the EOF symbol, or your lexer won't
 *  work.  */

    switch(yy_lexical_state) {
    case YYINITIAL:
	/* nothing special to do in the initial state */
	break;
		case COMMENT:
	    	yybegin(ESTOFERROR);	
	   		return new Symbol(TokenConstants.ERROR, "Se alcanzo EOF en comentario!");
	   	case STR:
	   		yybegin(ESTOFERROR);
	   		return new Symbol(TokenConstants.ERROR, "Se alcanzo EOF en String");
	   case ESTOFERROR:
	   break;
    }
    return new Symbol(TokenConstants.EOF);
%eofval}

%class CoolLexer
%cup
%state COMMENT,COMMENTLINEAL,ESTOFERROR,STR,STRERROR

%%

<YYINITIAL>"--"				{yybegin(COMMENTLINEAL);}  
<COMMENT,YYINITIAL>"(*" 		{yybegin(COMMENT);comentario++;}
<COMMENTLINEAL>\n 			{curr_lineno++; yybegin(YYINITIAL);}
<COMMENT>"*)"				{if (--comentario == 0) {yybegin(YYINITIAL);}}
<COMMENTLINEAL, COMMENT>.	{}
<COMMENT>\n					{curr_lineno++;}
<YYINITIAL>"*)"				{return new Symbol(TokenConstants.ERROR, "No se encontro *)");}



<YYINITIAL>[" "]|[\t]|[\r]|[\f]|[\u000B]       {} 
<YYINITIAL>"=>"				{return new Symbol(TokenConstants.DARROW); }
<YYINITIAL>\b 				{}                              
<YYINITIAL>\n          		{curr_lineno++;}



<YYINITIAL>["c"|"C"]["a"|"A"]["s"|"S"]["e"|"E"]			{return new Symbol(TokenConstants.CASE);}
<YYINITIAL>["c"|"C"]["l"|"L"]["a"|"A"]["s|S"]["s|S"]	{return new Symbol(TokenConstants.CLASS);}
<YYINITIAL>["e"|"E"]["l"|"L"]["s"|"S"]["e"|"E"]			{return new Symbol(TokenConstants.ELSE);}
<YYINITIAL>["e"|"E"]["s"|"S"]["a"|"A"]["c"|"C"]			{return new Symbol(TokenConstants.ESAC);}
<YYINITIAL>["f"]["\a"|"\A"]["\l"|"\L"]["\s"|"\S"]["\e"|"\E"]      {return new Symbol(TokenConstants.BOOL_CONST, "false"); }
<YYINITIAL>["t"]["\r"|"\R"]["\u"|"\U"]["\e"|"\E"]       {return new Symbol(TokenConstants.BOOL_CONST, "true"); }
<YYINITIAL>["f"|"F"]["i"|"I"]	{return new Symbol(TokenConstants.FI);}
<YYINITIAL>["i"|"I"]["f"|"F"]	{return new Symbol(TokenConstants.IF);}
<YYINITIAL>["i"|"I"]["n"|"N"]	{return new Symbol(TokenConstants.IN);}
<YYINITIAL>["i"|"I"]["n"|"N"]["h"|"H"]["e"|"E"]["r"|"R"]["i"|"I"]["t"|"T"]["s"|"S"]	{return new Symbol(TokenConstants.INHERITS);}
<YYINITIAL>["i"|"I"]["s"|"S"]["v"|"V"]["o"|"O"]["i"|"I"]["d"|"D"]	{return new Symbol(TokenConstants.ISVOID);}
<YYINITIAL>["l"|"L"]["e"|"E"]["t"|"T"]			{return new Symbol(TokenConstants.LET);}
<YYINITIAL>["l"|"L"]["o"|"O"]["o"|"O"]["p"|"P"]			{return new Symbol(TokenConstants.LOOP);}
<YYINITIAL>["n"|"N"]["e"|"E"]["w"|"W"]			{return new Symbol(TokenConstants.NEW);}
<YYINITIAL>["n"|"N"]["o"|"O"]["t"|"T"]        {return new Symbol(TokenConstants.NOT); }
<YYINITIAL>["o"|"O"]["f"|"F"]	{return new Symbol(TokenConstants.OF);}
<YYINITIAL>["p"|"P"]["o"|"O"]["o"|"O"]["l"|"L"]			{return new Symbol(TokenConstants.POOL);}
<YYINITIAL>["t"|"T"]["h"|"H"]["e"|"E"]["n"|"N"]			{return new Symbol(TokenConstants.THEN);}
<YYINITIAL>["w"|"W"]["h"|"H"]["i"|"I"]["l"|"L"]["e"|"E"]			{return new Symbol(TokenConstants.WHILE);}



<YYINITIAL>"."          {return new Symbol(TokenConstants.DOT); }
<YYINITIAL>"@"          {return new Symbol(TokenConstants.AT); }
<YYINITIAL>"~"          {return new Symbol(TokenConstants.NEG); }
<YYINITIAL>"/"          {return new Symbol(TokenConstants.DIV); }
<YYINITIAL>"*"          {return new Symbol(TokenConstants.MULT); }
<YYINITIAL>"+"          {return new Symbol(TokenConstants.PLUS); }
<YYINITIAL>"-"          {return new Symbol(TokenConstants.MINUS); }
<YYINITIAL>"<="         {return new Symbol(TokenConstants.LE); }
<YYINITIAL>"<"          {return new Symbol(TokenConstants.LT); }
<YYINITIAL>"="          {return new Symbol(TokenConstants.EQ); }
<YYINITIAL>"<-"         {return new Symbol(TokenConstants.ASSIGN); }
<YYINITIAL>","          {return new Symbol(TokenConstants.COMMA); }
<YYINITIAL>":"          {return new Symbol(TokenConstants.COLON); }
<YYINITIAL>";"          {return new Symbol(TokenConstants.SEMI); }
<YYINITIAL>[\{] 		{return new Symbol(TokenConstants.LBRACE); }
<YYINITIAL>[\}] 		{return new Symbol(TokenConstants.RBRACE); }
<YYINITIAL>[\(]  		{return new Symbol(TokenConstants.LPAREN); }
<YYINITIAL>[\)]  		{return new Symbol(TokenConstants.RPAREN); }
<YYINITIAL>"_"			{return new Symbol(TokenConstants.ERROR, "_");}
<YYINITIAL>"_"			{return new Symbol(TokenConstants.ERROR, "_");}
<YYINITIAL>["!"]			{return new Symbol(TokenConstants.ERROR, "!");}
<YYINITIAL>["#"]			{return new Symbol(TokenConstants.ERROR, "#");}
<YYINITIAL>["$"]			{return new Symbol(TokenConstants.ERROR, "$");}
<YYINITIAL>["%"]			{return new Symbol(TokenConstants.ERROR, "%");}
<YYINITIAL>["^"]			{return new Symbol(TokenConstants.ERROR, "^");}
<YYINITIAL>["&"]			{return new Symbol(TokenConstants.ERROR, "&");}
<YYINITIAL>[">"]			{return new Symbol(TokenConstants.ERROR, ">");}
<YYINITIAL>["?"]			{return new Symbol(TokenConstants.ERROR, "?");}
<YYINITIAL>["`"]			{return new Symbol(TokenConstants.ERROR, "`");}
<YYINITIAL>["["]			{return new Symbol(TokenConstants.ERROR, "[");}
<YYINITIAL>["]"]			{return new Symbol(TokenConstants.ERROR, "]");}
<YYINITIAL>["|"]			{return new Symbol(TokenConstants.ERROR, "|");}
<YYINITIAL>\\				{return new Symbol(TokenConstants.ERROR, "\\");}



<YYINITIAL>[0-9]+       {	AbstractSymbol inte = AbstractTable.inttable.addString(yytext());
							return new Symbol(TokenConstants.INT_CONST,inte); }
<YYINITIAL>[a-z][a-zA-Z0-9\_]*         {	AbstractSymbol obj = AbstractTable.idtable.addString(yytext());
							return new Symbol(TokenConstants.OBJECTID,obj); }
<YYINITIAL>[A-Z][a-zA-Z0-9\_]*       {	AbstractSymbol tip = AbstractTable.idtable.addString(yytext());
							return new Symbol(TokenConstants.TYPEID,tip); }



<YYINITIAL>error		{return new Symbol(TokenConstants.error);}



<YYINITIAL>[\"] 			{yybegin(STR); string_buf.setLength(0); }
<STR>\"						{yybegin(YYINITIAL); return new Symbol(TokenConstants.STR_CONST, AbstractTable.stringtable.addString(string_buf.toString()));}
<STR>\\b 					{string_buf.append("\b");}
<STR>\\f 					{string_buf.append("\f");}
<STR>\\t 					{string_buf.append("\t");}
<STR>\\\\n 					{string_buf.append("\\n");}
<STR>\\n 					{string_buf.append("\n");curr_lineno++; }
<STR>\\\n 					{string_buf.append("\n");}
<STR>\\\" 					{string_buf.append("\"");}
<STR>\\\\					{string_buf.append("\\");}
<STR>\\ 					{}
<STR>[^\"\0\n\\]+ 			{string_buf.append(yytext());}
<STR>\n						{yybegin(YYINITIAL); string_buf.setLength(0); return new Symbol(TokenConstants.ERROR, "Enter en String");}

<STR>\" 					{yybegin(YYINITIAL);
							String str = string_buf.toString();
							if (str.length() >= MAX_STR_CONST):{
								return new Symbol(TokenConstants.ERROR, "String es muy largo");
							}else{
								return new Symbol(TokenConstants.STR_CONST, new StringSymbol(str,str.length(),str.hashCode()))

							} }
<STR>\x00					{yybegin(STRERROR); return new Symbol(TokenConstants.ERROR, "String tiene null");}
<STRERROR>\"|\n				{yybegin(YYINITIAL);}
<STRERROR>.					{}


.                               { /* This rule should be the very last
                                     in your lexical specification and
                                     will match match everything not
                                     matched by other lexical rules. */
                                  return new Symbol(TokenConstants.ERROR, yytext());}


