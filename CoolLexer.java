/*
 *  The scanner definition for COOL.
 */
import java_cup.runtime.Symbol;


class CoolLexer implements java_cup.runtime.Scanner {
	private final int YY_BUFFER_SIZE = 512;
	private final int YY_F = -1;
	private final int YY_NO_STATE = -1;
	private final int YY_NOT_ACCEPT = 0;
	private final int YY_START = 1;
	private final int YY_END = 2;
	private final int YY_NO_ANCHOR = 4;
	private final int YY_BOL = 128;
	private final int YY_EOF = 129;

/*  Stuff enclosed in %{ %} is copied verbatim to the lexer class
 *  definition, all the extra variables/functions you want to use in the
 *  lexer actions should go here.  Don't remove or modify anything that
 *  was there initially.  */
    // Max size of string constants
    static int MAX_STR_CONST = 1025;
    // For assembling string constants
    StringBuffer string_buf = new StringBuffer();
    private int curr_lineno = 1;
    int white = 0;
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
	private java.io.BufferedReader yy_reader;
	private int yy_buffer_index;
	private int yy_buffer_read;
	private int yy_buffer_start;
	private int yy_buffer_end;
	private char yy_buffer[];
	private boolean yy_at_bol;
	private int yy_lexical_state;

	CoolLexer (java.io.Reader reader) {
		this ();
		if (null == reader) {
			throw (new Error("Error: Bad input stream initializer."));
		}
		yy_reader = new java.io.BufferedReader(reader);
	}

	CoolLexer (java.io.InputStream instream) {
		this ();
		if (null == instream) {
			throw (new Error("Error: Bad input stream initializer."));
		}
		yy_reader = new java.io.BufferedReader(new java.io.InputStreamReader(instream));
	}

	private CoolLexer () {
		yy_buffer = new char[YY_BUFFER_SIZE];
		yy_buffer_read = 0;
		yy_buffer_index = 0;
		yy_buffer_start = 0;
		yy_buffer_end = 0;
		yy_at_bol = true;
		yy_lexical_state = YYINITIAL;

/*  Stuff enclosed in %init{ %init} is copied verbatim to the lexer
 *  class constructor, all the extra initialization you want to do should
 *  go here.  Don't remove or modify anything that was there initially. */
    // empty for now
	}

	private boolean yy_eof_done = false;
	private final int ESTOFERROR = 2;
	private final int YYINITIAL = 0;
	private final int COMMENT = 1;
	private final int COMMENTLINEA = 3;
	private final int yy_state_dtrans[] = {
		0,
		206,
		208,
		208
	};
	private void yybegin (int state) {
		yy_lexical_state = state;
	}
	private int yy_advance ()
		throws java.io.IOException {
		int next_read;
		int i;
		int j;

		if (yy_buffer_index < yy_buffer_read) {
			return yy_buffer[yy_buffer_index++];
		}

		if (0 != yy_buffer_start) {
			i = yy_buffer_start;
			j = 0;
			while (i < yy_buffer_read) {
				yy_buffer[j] = yy_buffer[i];
				++i;
				++j;
			}
			yy_buffer_end = yy_buffer_end - yy_buffer_start;
			yy_buffer_start = 0;
			yy_buffer_read = j;
			yy_buffer_index = j;
			next_read = yy_reader.read(yy_buffer,
					yy_buffer_read,
					yy_buffer.length - yy_buffer_read);
			if (-1 == next_read) {
				return YY_EOF;
			}
			yy_buffer_read = yy_buffer_read + next_read;
		}

		while (yy_buffer_index >= yy_buffer_read) {
			if (yy_buffer_index >= yy_buffer.length) {
				yy_buffer = yy_double(yy_buffer);
			}
			next_read = yy_reader.read(yy_buffer,
					yy_buffer_read,
					yy_buffer.length - yy_buffer_read);
			if (-1 == next_read) {
				return YY_EOF;
			}
			yy_buffer_read = yy_buffer_read + next_read;
		}
		return yy_buffer[yy_buffer_index++];
	}
	private void yy_move_end () {
		if (yy_buffer_end > yy_buffer_start &&
		    '\n' == yy_buffer[yy_buffer_end-1])
			yy_buffer_end--;
		if (yy_buffer_end > yy_buffer_start &&
		    '\r' == yy_buffer[yy_buffer_end-1])
			yy_buffer_end--;
	}
	private boolean yy_last_was_cr=false;
	private void yy_mark_start () {
		yy_buffer_start = yy_buffer_index;
	}
	private void yy_mark_end () {
		yy_buffer_end = yy_buffer_index;
	}
	private void yy_to_mark () {
		yy_buffer_index = yy_buffer_end;
		yy_at_bol = (yy_buffer_end > yy_buffer_start) &&
		            ('\r' == yy_buffer[yy_buffer_end-1] ||
		             '\n' == yy_buffer[yy_buffer_end-1] ||
		             2028/*LS*/ == yy_buffer[yy_buffer_end-1] ||
		             2029/*PS*/ == yy_buffer[yy_buffer_end-1]);
	}
	private java.lang.String yytext () {
		return (new java.lang.String(yy_buffer,
			yy_buffer_start,
			yy_buffer_end - yy_buffer_start));
	}
	private int yylength () {
		return yy_buffer_end - yy_buffer_start;
	}
	private char[] yy_double (char buf[]) {
		int i;
		char newbuf[];
		newbuf = new char[2*buf.length];
		for (i = 0; i < buf.length; ++i) {
			newbuf[i] = buf[i];
		}
		return newbuf;
	}
	private final int YY_E_INTERNAL = 0;
	private final int YY_E_MATCH = 1;
	private java.lang.String yy_error_string[] = {
		"Error: Internal error.\n",
		"Error: Unmatched input.\n"
	};
	private void yy_error (int code,boolean fatal) {
		java.lang.System.out.print(yy_error_string[code]);
		java.lang.System.out.flush();
		if (fatal) {
			throw new Error("Fatal Error.\n");
		}
	}
	private int[][] unpackFromString(int size1, int size2, String st) {
		int colonIndex = -1;
		String lengthString;
		int sequenceLength = 0;
		int sequenceInteger = 0;

		int commaIndex;
		String workString;

		int res[][] = new int[size1][size2];
		for (int i= 0; i < size1; i++) {
			for (int j= 0; j < size2; j++) {
				if (sequenceLength != 0) {
					res[i][j] = sequenceInteger;
					sequenceLength--;
					continue;
				}
				commaIndex = st.indexOf(',');
				workString = (commaIndex==-1) ? st :
					st.substring(0, commaIndex);
				st = st.substring(commaIndex+1);
				colonIndex = workString.indexOf(':');
				if (colonIndex == -1) {
					res[i][j]=Integer.parseInt(workString);
					continue;
				}
				lengthString =
					workString.substring(colonIndex+1);
				sequenceLength=Integer.parseInt(lengthString);
				workString=workString.substring(0,colonIndex);
				sequenceInteger=Integer.parseInt(workString);
				res[i][j] = sequenceInteger;
				sequenceLength--;
			}
		}
		return res;
	}
	private int yy_acpt[] = {
		/* 0 */ YY_NOT_ACCEPT,
		/* 1 */ YY_NO_ANCHOR,
		/* 2 */ YY_NO_ANCHOR,
		/* 3 */ YY_NO_ANCHOR,
		/* 4 */ YY_NO_ANCHOR,
		/* 5 */ YY_NO_ANCHOR,
		/* 6 */ YY_NO_ANCHOR,
		/* 7 */ YY_NO_ANCHOR,
		/* 8 */ YY_NO_ANCHOR,
		/* 9 */ YY_NO_ANCHOR,
		/* 10 */ YY_NO_ANCHOR,
		/* 11 */ YY_NO_ANCHOR,
		/* 12 */ YY_NO_ANCHOR,
		/* 13 */ YY_NO_ANCHOR,
		/* 14 */ YY_NO_ANCHOR,
		/* 15 */ YY_NO_ANCHOR,
		/* 16 */ YY_NO_ANCHOR,
		/* 17 */ YY_NO_ANCHOR,
		/* 18 */ YY_NO_ANCHOR,
		/* 19 */ YY_NO_ANCHOR,
		/* 20 */ YY_NO_ANCHOR,
		/* 21 */ YY_NO_ANCHOR,
		/* 22 */ YY_NO_ANCHOR,
		/* 23 */ YY_NO_ANCHOR,
		/* 24 */ YY_NO_ANCHOR,
		/* 25 */ YY_NO_ANCHOR,
		/* 26 */ YY_NO_ANCHOR,
		/* 27 */ YY_NO_ANCHOR,
		/* 28 */ YY_NO_ANCHOR,
		/* 29 */ YY_NO_ANCHOR,
		/* 30 */ YY_NO_ANCHOR,
		/* 31 */ YY_NO_ANCHOR,
		/* 32 */ YY_NO_ANCHOR,
		/* 33 */ YY_NO_ANCHOR,
		/* 34 */ YY_NO_ANCHOR,
		/* 35 */ YY_NO_ANCHOR,
		/* 36 */ YY_NO_ANCHOR,
		/* 37 */ YY_NO_ANCHOR,
		/* 38 */ YY_NO_ANCHOR,
		/* 39 */ YY_NO_ANCHOR,
		/* 40 */ YY_NO_ANCHOR,
		/* 41 */ YY_NO_ANCHOR,
		/* 42 */ YY_NO_ANCHOR,
		/* 43 */ YY_NO_ANCHOR,
		/* 44 */ YY_NO_ANCHOR,
		/* 45 */ YY_NO_ANCHOR,
		/* 46 */ YY_NO_ANCHOR,
		/* 47 */ YY_NO_ANCHOR,
		/* 48 */ YY_NO_ANCHOR,
		/* 49 */ YY_NO_ANCHOR,
		/* 50 */ YY_NO_ANCHOR,
		/* 51 */ YY_NO_ANCHOR,
		/* 52 */ YY_NO_ANCHOR,
		/* 53 */ YY_NOT_ACCEPT,
		/* 54 */ YY_NO_ANCHOR,
		/* 55 */ YY_NO_ANCHOR,
		/* 56 */ YY_NO_ANCHOR,
		/* 57 */ YY_NO_ANCHOR,
		/* 58 */ YY_NO_ANCHOR,
		/* 59 */ YY_NO_ANCHOR,
		/* 60 */ YY_NO_ANCHOR,
		/* 61 */ YY_NO_ANCHOR,
		/* 62 */ YY_NO_ANCHOR,
		/* 63 */ YY_NO_ANCHOR,
		/* 64 */ YY_NO_ANCHOR,
		/* 65 */ YY_NO_ANCHOR,
		/* 66 */ YY_NO_ANCHOR,
		/* 67 */ YY_NO_ANCHOR,
		/* 68 */ YY_NO_ANCHOR,
		/* 69 */ YY_NO_ANCHOR,
		/* 70 */ YY_NO_ANCHOR,
		/* 71 */ YY_NO_ANCHOR,
		/* 72 */ YY_NO_ANCHOR,
		/* 73 */ YY_NO_ANCHOR,
		/* 74 */ YY_NO_ANCHOR,
		/* 75 */ YY_NO_ANCHOR,
		/* 76 */ YY_NOT_ACCEPT,
		/* 77 */ YY_NO_ANCHOR,
		/* 78 */ YY_NO_ANCHOR,
		/* 79 */ YY_NO_ANCHOR,
		/* 80 */ YY_NO_ANCHOR,
		/* 81 */ YY_NO_ANCHOR,
		/* 82 */ YY_NO_ANCHOR,
		/* 83 */ YY_NO_ANCHOR,
		/* 84 */ YY_NO_ANCHOR,
		/* 85 */ YY_NO_ANCHOR,
		/* 86 */ YY_NO_ANCHOR,
		/* 87 */ YY_NO_ANCHOR,
		/* 88 */ YY_NO_ANCHOR,
		/* 89 */ YY_NO_ANCHOR,
		/* 90 */ YY_NO_ANCHOR,
		/* 91 */ YY_NO_ANCHOR,
		/* 92 */ YY_NO_ANCHOR,
		/* 93 */ YY_NO_ANCHOR,
		/* 94 */ YY_NO_ANCHOR,
		/* 95 */ YY_NO_ANCHOR,
		/* 96 */ YY_NOT_ACCEPT,
		/* 97 */ YY_NO_ANCHOR,
		/* 98 */ YY_NO_ANCHOR,
		/* 99 */ YY_NO_ANCHOR,
		/* 100 */ YY_NO_ANCHOR,
		/* 101 */ YY_NO_ANCHOR,
		/* 102 */ YY_NO_ANCHOR,
		/* 103 */ YY_NO_ANCHOR,
		/* 104 */ YY_NO_ANCHOR,
		/* 105 */ YY_NO_ANCHOR,
		/* 106 */ YY_NO_ANCHOR,
		/* 107 */ YY_NO_ANCHOR,
		/* 108 */ YY_NOT_ACCEPT,
		/* 109 */ YY_NO_ANCHOR,
		/* 110 */ YY_NO_ANCHOR,
		/* 111 */ YY_NO_ANCHOR,
		/* 112 */ YY_NO_ANCHOR,
		/* 113 */ YY_NO_ANCHOR,
		/* 114 */ YY_NOT_ACCEPT,
		/* 115 */ YY_NO_ANCHOR,
		/* 116 */ YY_NO_ANCHOR,
		/* 117 */ YY_NO_ANCHOR,
		/* 118 */ YY_NOT_ACCEPT,
		/* 119 */ YY_NO_ANCHOR,
		/* 120 */ YY_NO_ANCHOR,
		/* 121 */ YY_NOT_ACCEPT,
		/* 122 */ YY_NO_ANCHOR,
		/* 123 */ YY_NO_ANCHOR,
		/* 124 */ YY_NOT_ACCEPT,
		/* 125 */ YY_NO_ANCHOR,
		/* 126 */ YY_NO_ANCHOR,
		/* 127 */ YY_NOT_ACCEPT,
		/* 128 */ YY_NO_ANCHOR,
		/* 129 */ YY_NO_ANCHOR,
		/* 130 */ YY_NOT_ACCEPT,
		/* 131 */ YY_NO_ANCHOR,
		/* 132 */ YY_NO_ANCHOR,
		/* 133 */ YY_NOT_ACCEPT,
		/* 134 */ YY_NO_ANCHOR,
		/* 135 */ YY_NO_ANCHOR,
		/* 136 */ YY_NOT_ACCEPT,
		/* 137 */ YY_NO_ANCHOR,
		/* 138 */ YY_NO_ANCHOR,
		/* 139 */ YY_NOT_ACCEPT,
		/* 140 */ YY_NO_ANCHOR,
		/* 141 */ YY_NO_ANCHOR,
		/* 142 */ YY_NOT_ACCEPT,
		/* 143 */ YY_NO_ANCHOR,
		/* 144 */ YY_NO_ANCHOR,
		/* 145 */ YY_NOT_ACCEPT,
		/* 146 */ YY_NO_ANCHOR,
		/* 147 */ YY_NO_ANCHOR,
		/* 148 */ YY_NOT_ACCEPT,
		/* 149 */ YY_NO_ANCHOR,
		/* 150 */ YY_NO_ANCHOR,
		/* 151 */ YY_NOT_ACCEPT,
		/* 152 */ YY_NO_ANCHOR,
		/* 153 */ YY_NO_ANCHOR,
		/* 154 */ YY_NOT_ACCEPT,
		/* 155 */ YY_NO_ANCHOR,
		/* 156 */ YY_NO_ANCHOR,
		/* 157 */ YY_NOT_ACCEPT,
		/* 158 */ YY_NO_ANCHOR,
		/* 159 */ YY_NO_ANCHOR,
		/* 160 */ YY_NOT_ACCEPT,
		/* 161 */ YY_NO_ANCHOR,
		/* 162 */ YY_NO_ANCHOR,
		/* 163 */ YY_NOT_ACCEPT,
		/* 164 */ YY_NO_ANCHOR,
		/* 165 */ YY_NO_ANCHOR,
		/* 166 */ YY_NOT_ACCEPT,
		/* 167 */ YY_NO_ANCHOR,
		/* 168 */ YY_NO_ANCHOR,
		/* 169 */ YY_NOT_ACCEPT,
		/* 170 */ YY_NO_ANCHOR,
		/* 171 */ YY_NO_ANCHOR,
		/* 172 */ YY_NOT_ACCEPT,
		/* 173 */ YY_NO_ANCHOR,
		/* 174 */ YY_NO_ANCHOR,
		/* 175 */ YY_NOT_ACCEPT,
		/* 176 */ YY_NO_ANCHOR,
		/* 177 */ YY_NO_ANCHOR,
		/* 178 */ YY_NOT_ACCEPT,
		/* 179 */ YY_NO_ANCHOR,
		/* 180 */ YY_NOT_ACCEPT,
		/* 181 */ YY_NO_ANCHOR,
		/* 182 */ YY_NOT_ACCEPT,
		/* 183 */ YY_NO_ANCHOR,
		/* 184 */ YY_NOT_ACCEPT,
		/* 185 */ YY_NO_ANCHOR,
		/* 186 */ YY_NOT_ACCEPT,
		/* 187 */ YY_NO_ANCHOR,
		/* 188 */ YY_NOT_ACCEPT,
		/* 189 */ YY_NO_ANCHOR,
		/* 190 */ YY_NOT_ACCEPT,
		/* 191 */ YY_NO_ANCHOR,
		/* 192 */ YY_NOT_ACCEPT,
		/* 193 */ YY_NO_ANCHOR,
		/* 194 */ YY_NOT_ACCEPT,
		/* 195 */ YY_NO_ANCHOR,
		/* 196 */ YY_NOT_ACCEPT,
		/* 197 */ YY_NO_ANCHOR,
		/* 198 */ YY_NOT_ACCEPT,
		/* 199 */ YY_NO_ANCHOR,
		/* 200 */ YY_NOT_ACCEPT,
		/* 201 */ YY_NO_ANCHOR,
		/* 202 */ YY_NOT_ACCEPT,
		/* 203 */ YY_NO_ANCHOR,
		/* 204 */ YY_NOT_ACCEPT,
		/* 205 */ YY_NO_ANCHOR,
		/* 206 */ YY_NOT_ACCEPT,
		/* 207 */ YY_NO_ANCHOR,
		/* 208 */ YY_NOT_ACCEPT,
		/* 209 */ YY_NO_ANCHOR,
		/* 210 */ YY_NOT_ACCEPT,
		/* 211 */ YY_NOT_ACCEPT,
		/* 212 */ YY_NO_ANCHOR,
		/* 213 */ YY_NOT_ACCEPT,
		/* 214 */ YY_NO_ANCHOR,
		/* 215 */ YY_NO_ANCHOR,
		/* 216 */ YY_NO_ANCHOR,
		/* 217 */ YY_NO_ANCHOR,
		/* 218 */ YY_NO_ANCHOR,
		/* 219 */ YY_NO_ANCHOR,
		/* 220 */ YY_NOT_ACCEPT,
		/* 221 */ YY_NO_ANCHOR,
		/* 222 */ YY_NO_ANCHOR,
		/* 223 */ YY_NO_ANCHOR,
		/* 224 */ YY_NO_ANCHOR,
		/* 225 */ YY_NO_ANCHOR,
		/* 226 */ YY_NO_ANCHOR,
		/* 227 */ YY_NO_ANCHOR
	};
	private int yy_cmap[] = unpackFromString(1,130,
"4,62:7,12,8,6,62,8,5,62:18,7,62,61,62:5,1,3,2,41,44,9,32,40,49:10,45,46,42," +
"10,11,62,33,16,63,13,63,18,24,63,27,25,63:2,14,63,26,29,30,63,20,17,28,21,6" +
"3,31,63:3,62,22,62:2,60,62,50,51,52,39,53,23,51,54,35,51:2,55,51,43,38,56,5" +
"1,57,36,19,58,37,59,51:3,47,15,48,34,62,0:2")[0];

	private int yy_rmap[] = unpackFromString(1,228,
"0,1,2,3,1:4,4,5,1,6,7,1:5,8,1:5,9,1:2,10,1,11,1:4,12,1:2,13,1,14,1,15,1:4,1" +
"6,1:2,13,1,2,1,17,18:2,19,20,1,11,21,19,1,19,22,23,1,15,19,23,13,1,19,13,19" +
",24,25,26,27,28,29,19,30,13,31,13,1,19:2,13:2,19:2,13:2,1,32,33,13:3,19,23," +
"13:2,19,13:2,34,35,36,19,13,19,37,38,39,13,40,41,42,43,44,45,46,47,48,49,50" +
",51,52,53,54,29,55,56,57,58,59,60,61,62,63,64,65,66,67,68,69,70,71,72,73,74" +
",75,76,77,78,79,80,81,82,83,84,85,86,87,88,89,90,91,92,93,94,95,96,97,98,99" +
",100,31,101,102,103,104,105,106,107,108,109,110,111,23,112,15,113,22,114,11" +
"5,116,16,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,13" +
"3,134,135,136,137,138,139,140,141,142,143")[0];

	private int yy_nxt[][] = unpackFromString(144,64,
"1,2,3,4,5,6,7,55,6,8,9,5,10,11,209,54,56:2,219,12,56:2,5,57,78,97,222,56,22" +
"3,109,224,225,13,14,15,79,98:2,110,98,16,17,18,116,19,20,21,22,23,24,98:2,1" +
"20,123,98,126,129,98:2,132,5,77,5,56,-1:66,25,-1:64,26,-1:69,121,-1:65,30,-" +
"1:59,210,-1:5,56,115,210,119,56:5,-1,56:9,-1:3,56:5,-1:3,56,-1:5,56,119,56:" +
"4,115,56:5,-1:2,56,-1:7,127,-1:5,98:2,127,98:4,135,98,130,98:4,138,98:4,-1:" +
"3,98:5,-1:3,98,-1:5,98:5,138,98:2,135,98:3,-1:2,98,-1:9,32,33,-1:102,24,-1:" +
"21,34,-1:7,34,148,151,154,62,-1:5,157,-1,160,62,163,-1,35,-1:3,157,151,-1,1" +
"63,-1:11,148,-1:2,154,160,-1:4,35,-1:11,160,-1:7,160,-1:11,160,-1:26,160,-1" +
":16,39,-1:5,40,41,39,-1,192,64,-1:7,42,-1:3,43,-1:5,192,-1:6,42,-1:8,40,64," +
"-1,41,43,-1:20,98:2,-1,98:6,-1,98:9,-1:3,98:5,-1:3,98,-1:5,98:12,-1:2,98,-1" +
":7,46,-1:7,46,-1,71,47,-1,200,-1:15,71,-1:16,47,-1:3,200,-1:13,47,-1:7,47,-" +
"1:2,47,-1:34,47,-1:17,202,-1:7,202,-1:9,202,-1:9,202,-1:35,166,-1:7,166,211" +
",151,-1:18,151,-1:13,211,-1:20,27,-1:6,53,27,76,96,108,-1:4,28:2,58,29,114," +
"-1,118,-1:5,58,96,-1,118,-1:4,29,-1:6,76,-1:2,108,114,53,-1:21,56:2,-1,56:6" +
",-1,56:9,-1:3,56:5,-1:3,56,-1:5,56:12,-1:2,56,-1:7,80,-1:5,98:2,80,141,98:5" +
",133,98:2,99,98:6,-1:3,99,98:4,-1:3,98,-1:5,98,141,98:10,-1:2,98,-1:7,160,-" +
"1:5,56:2,160,56:6,-1,56:4,216,56:4,-1:3,56:5,-1:3,56,-1:5,56:5,216,56:6,-1:" +
"2,56,-1:7,200,-1:7,200,-1:4,200,-1:36,200,-1:13,71,-1:7,71,-1,71,-1:18,71,-" +
"1:30,52,-1:67,169,-1:7,169,-1,169,-1:18,169,-1:28,145:3,-1,145,-1,145:15,-1" +
",145:38,-1,145:2,-1:7,58,-1:5,56:2,58,56:6,-1,56:2,111,56:6,-1:3,111,56:4,-" +
"1:3,56,-1:5,56:12,-1:2,56,-1:7,59,-1:5,98:2,59,98:6,-1,100:2,98,82,98:5,-1:" +
"3,98,227,98:3,-1:3,82,-1:5,98:12,-1:2,98,-1:7,188,-1:6,188:2,-1:6,188,-1:32" +
",188,-1:15,160,-1:5,98:2,160,98:6,-1,98:4,179,98:4,-1:3,98:5,-1:3,98,-1:5,9" +
"8:5,179,98:6,-1:2,98,-1:7,43,-1:7,43,-1:14,43,-1:25,43,-1:14,172,-1:7,172:2" +
",-1:33,172,-1:20,59,-1:5,56:2,59,56:6,-1,81:2,56,60,56:5,-1:3,56:5,-1:3,60," +
"-1:5,56:12,-1:2,56,-1:7,62,-1:7,62,-1:3,62,-1:8,62,-1:2,35,-1:27,35,-1:11,3" +
"1,-1:5,56:2,31,56:6,-1,61:2,56:7,-1:3,56:5,-1:3,56,-1:5,56:12,-1:2,56,-1:7," +
"31,-1:5,98:2,31,98:6,-1,83:2,98:7,-1:3,98:5,-1:3,98,-1:5,98:12,-1:2,98,-1:7" +
",175,-1:7,175,-1:2,154,-1:6,157,-1:9,157,-1:17,154,-1:17,211,-1:5,56:2,211," +
"221,56:5,-1,56:9,-1:3,56:5,-1:3,56,-1:5,56,221,56:10,-1:2,56,-1:7,136,-1:5," +
"98:2,136,98:2,144,98:3,-1,98:9,-1:3,98:3,147,98,-1:3,98,-1:5,98:4,144,98:7," +
"-1:2,98,-1:7,163,-1:7,163,-1:13,163,-1:8,163,-1:32,169,-1:5,56:2,169,56,140" +
",56:4,-1,56:9,-1:3,56,140,56:3,-1:3,56,-1:5,56:12,-1:2,56,-1:7,210,-1:5,98," +
"150,210,153,98:5,-1,98:9,-1:3,98:5,-1:3,98,-1:5,98,153,98:4,150,98:5,-1:2,9" +
"8,-1,36:5,-1,36:57,-1:7,62,-1:5,56:2,62,56:3,101,56:2,-1,56:5,101,56:3,-1:3" +
",56:5,-1:3,56,-1:5,56:12,-1:2,56,-1:7,220,-1:5,98,156,220,98,159,98:4,-1,98" +
":9,-1:3,98,159,98:3,-1:3,98,-1:5,98:6,156,98:5,-1:2,98,-1:7,84,-1:7,84,-1:3" +
",62,-1:8,62,180,-1:8,180,-1:32,180,-1:5,56:2,180,56:6,-1,56:6,143,56:2,-1:3" +
",56:3,143,56,-1:3,56,-1:5,56:12,-1:2,56,-1:7,124,-1:5,98:2,124,98:2,162,98:" +
"3,-1,98:6,165,98:2,-1:3,98:3,165,98,-1:3,98,-1:5,98:4,162,98:7,-1:2,98,-1:7" +
",184,-1:7,184,-1:2,154,-1:2,186:2,-1:30,154,-1:4,186,-1:12,172,-1:5,56:2,17" +
"2,149,56:5,-1,56:9,-1:3,56:5,-1:3,56,-1:5,56,149,56:10,-1:2,56,-1:7,213,-1:" +
"5,98:2,213,98:6,-1,98:6,218,98:2,-1:3,98:3,218,98,-1:3,98,-1:5,98:12,-1:2,9" +
"8,-1:7,186,-1:7,186,-1:5,186:2,-1:35,186,-1:12,35,-1:5,56:2,35,56:6,-1,56:8" +
",63,-1:3,56:5,-1:3,56,-1:5,56:10,63,56,-1:2,56,-1:7,142,-1:5,98:2,142,98:6," +
"-1,98:4,168,98:4,-1:3,98:5,-1:3,98,-1:5,98:5,168,98:6,-1:2,98,-1:7,154,-1:5" +
",56:2,154,56:2,152,56:3,-1,56:9,-1:3,56:5,-1:3,56,-1:5,56:4,152,56:7,-1:2,5" +
"6,-1:7,186,-1:5,98:2,186,98:5,171,186,98:9,-1:3,98:5,-1:3,98,-1:5,98:9,171," +
"98:2,-1:2,98,-1:7,35,-1:7,35,-1:15,35,-1:27,35,-1:11,157,-1:5,56:2,157,56:6" +
",-1,56:2,158,56:6,-1:3,158,56:4,-1:3,56,-1:5,56:12,-1:2,56,-1:7,154,-1:5,98" +
":2,154,98:2,174,98:3,-1,98:9,-1:3,98:5,-1:3,98,-1:5,98:4,174,98:7,-1:2,98,-" +
"1:7,154,-1:7,154,-1:2,154,-1:34,154,-1:17,44,-1:5,56:2,44,56:2,91,56:3,-1,5" +
"6:9,-1:3,56:5,-1:3,56,-1:5,56:4,91,56:7,-1:2,56,-1:7,188,-1:5,98,177,188,98" +
":6,188,98:9,-1:3,98:5,-1:3,98,-1:5,98:6,177,98:5,-1:2,98,-1:7,157,-1:7,157," +
"-1:9,157,-1:9,157,-1:35,43,-1:5,56:2,43,56:6,-1,56:7,68,56,-1:3,56:5,-1:3,5" +
"6,-1:5,56:7,68,56:4,-1:2,56,-1:7,35,-1:5,98:2,35,98:6,-1,98:8,85,-1:3,98:5," +
"-1:3,98,-1:5,98:10,85,98,-1:2,98,-1,145:3,-1,145,-1,145:15,-1,145:38,38,145" +
":2,-1:7,86,-1:5,56:2,86,56:2,113,56:3,-1,56:9,-1:3,56:5,-1:3,56,-1:5,56:4,1" +
"13,56:7,-1:2,56,-1:13,98:2,-1,98:3,37,98:2,-1,98:9,-1:3,98:5,-1:3,98,-1:5,9" +
"8:12,-1:2,98,-1:7,65,-1:5,40,-1,65,-1,192,-1:18,192,-1:15,40,-1:18,40,-1:5," +
"87,56,40,56:6,-1,56:9,-1:3,56:5,-1:3,56,-1:5,56:3,87,56:8,-1:2,56,-1:7,211," +
"-1:5,98:2,211,217,98:5,-1,98:9,-1:3,98:5,-1:3,98,-1:5,98,217,98:10,-1:2,98," +
"-1:7,86,-1:7,86,-1:2,86,-1:34,86,-1:17,42,-1:5,56:2,42,56:6,-1,56:3,105,56:" +
"5,-1:3,56:5,-1:3,105,-1:5,56:12,-1:2,56,-1:7,169,-1:5,98:2,169,98,181,98:4," +
"-1,98:9,-1:3,98,181,98:3,-1:3,98,-1:5,98:12,-1:2,98,-1:7,42,-1:7,42,-1:10,4" +
"2,-1:16,42,-1:27,66,-1:5,56,88,66,56:6,-1,56:9,-1:3,56:5,-1:3,56,-1:5,56:6," +
"88,56:5,-1:2,56,-1:7,151,-1:5,98:2,151,98,183,98:4,-1,98:9,-1:3,98,183,98:3" +
",-1:3,98,-1:5,98:12,-1:2,98,-1:7,194,-1:6,194:2,-1:39,194,-1:15,194,-1:5,56" +
",167,194,56:6,-1,56:9,-1:3,56:5,-1:3,56,-1:5,56:6,167,56:5,-1:2,56,-1:7,172" +
",-1:5,98:2,172,185,98:5,-1,98:9,-1:3,98:5,-1:3,98,-1:5,98,185,98:10,-1:2,98" +
",-1:7,196,-1:7,196,-1:2,196,-1:34,196,-1:17,71,-1:5,56:2,71,56,92,56:4,-1,5" +
"6:9,-1:3,56,92,56:3,-1:3,56,-1:5,56:12,-1:2,56,-1:7,62,-1:5,98:2,62,98:3,11" +
"2,98:2,-1,98:5,112,98:3,-1:3,98:5,-1:3,98,-1:5,98:12,-1:2,98,-1:7,43,-1:6,6" +
"6,43,-1:14,43,-1:24,66,43,-1:14,200,-1:5,56:2,200,56:4,170,56,-1,56:9,-1:3," +
"56:5,-1:3,56,-1:5,56:8,170,56:3,-1:2,56,-1:7,180,-1:5,98:2,180,98:6,-1,98:6" +
",187,98:2,-1:3,98:3,187,98,-1:3,98,-1:5,98:12,-1:2,98,-1:7,102,-1:7,102,-1," +
"192,86,-1:17,192,-1:16,86,-1:17,47,-1:5,56:2,47,56:2,72,56:3,-1,56:9,-1:3,5" +
"6:5,-1:3,56,-1:5,56:4,72,56:7,-1:2,56,-1:7,157,-1:5,98:2,157,98:6,-1,98:2,1" +
"91,98:6,-1:3,191,98:4,-1:3,98,-1:5,98:12,-1:2,98,-1:7,44,-1:7,44,-1:2,44,-1" +
":34,44,-1:17,202,-1:5,56:2,202,56:6,-1,56:2,173,56:6,-1:3,173,56:4,-1:3,56," +
"-1:5,56:12,-1:2,56,-1:7,45,-1:5,98:2,45,98:2,70,98:3,45,98:9,-1:3,98:5,-1:3" +
",98,-1:5,98:4,70,98:7,-1:2,98,-1:7,40,-1:5,40,-1,40,-1:36,40,-1:18,204,-1:5" +
",56:2,204,56:3,176,56:2,-1,56:5,176,56:3,-1:3,56:5,-1:3,56,-1:5,56:12,-1:2," +
"56,-1:7,42,-1:5,98:2,42,98:6,-1,98:3,89,98:5,-1:3,98:5,-1:3,89,-1:5,98:12,-" +
"1:2,98,-1:7,67,-1:6,194,67,-1:10,42,-1:16,42,-1:11,194,-1:15,50,-1:5,56:2,5" +
"0,56,74,56:4,-1,56:9,-1:3,56,74,56:3,-1:3,56,-1:5,56:12,-1:2,56,-1:7,198,-1" +
":5,98:2,198,98,193,98:4,198,98:9,-1:3,98,193,98:3,-1:3,98,-1:5,98:12,-1:2,9" +
"8,-1:7,69,-1:7,69,-1,192,44,-1:17,192,-1:16,44,-1:17,196,-1:5,98:2,196,98:2" +
",195,98:3,-1,98:9,-1:3,98:5,-1:3,98,-1:5,98:4,195,98:7,-1:2,98,-1:7,44,-1:5" +
",98:2,44,98:2,106,98:3,-1,98:9,-1:3,98:5,-1:3,98,-1:5,98:4,106,98:7,-1:2,98" +
",-1:7,86,-1:5,40,-1,86,-1:2,86,-1:33,40,86,-1:17,86,-1:5,98:2,86,98:2,117,9" +
"8:3,-1,98:9,-1:3,98:5,-1:3,98,-1:5,98:4,117,98:7,-1:2,98,-1:7,45,-1:7,45,-1" +
":2,45,-1:3,45,-1:3,42,-1:16,42,-1:9,45,-1:17,40,-1:5,103,98,40,98:6,-1,98:9" +
",-1:3,98:5,-1:3,98,-1:5,98:3,103,98:8,-1:2,98,-1:7,45,-1:7,45,-1:2,45,-1:3," +
"45,-1:30,45,-1:17,43,-1:5,98:2,43,98:6,-1,98:7,90,98,-1:3,98:5,-1:3,98,-1:5" +
",98:7,90,98:4,-1:2,98,-1:7,198,-1:7,198,-1,198,-1:4,198,-1:13,198,-1:34,66," +
"-1:5,98,104,66,98:6,-1,98:9,-1:3,98:5,-1:3,98,-1:5,98:6,104,98:5,-1:2,98,-1" +
":7,66,-1:6,66:2,-1:39,66,-1:15,194,-1:5,98,199,194,98:6,-1,98:9,-1:3,98:5,-" +
"1:3,98,-1:5,98:6,199,98:5,-1:2,98,-1:7,48,-1:5,98:2,48,98:2,73,98:3,48,98:9" +
",-1:3,98:5,-1:3,98,-1:5,98:4,73,98:7,-1:2,98,-1:7,200,-1:5,98:2,200,98:4,20" +
"1,98,-1,98:9,-1:3,98:5,-1:3,98,-1:5,98:8,201,98:3,-1:2,98,-1:7,71,-1:5,98:2" +
",71,98,107,98:4,-1,98:9,-1:3,98,107,98:3,-1:3,98,-1:5,98:12,-1:2,98,-1:7,48" +
",-1:7,48,-1:2,48,-1:3,48,-1:30,48,-1:17,47,-1:5,98:2,47,98:2,93,98:3,-1,98:" +
"9,-1:3,98:5,-1:3,98,-1:5,98:4,93,98:7,-1:2,98,-1:7,202,-1:5,98:2,202,98:6,-" +
"1,98:2,205,98:6,-1:3,205,98:4,-1:3,98,-1:5,98:12,-1:2,98,-1:7,204,-1:7,204," +
"-1:3,204,-1:8,204,-1:48,98:2,-1,98:6,-1,98:9,-1:3,98:4,49,-1:3,98,-1:5,98:1" +
"2,-1:2,98,-1:7,50,-1:7,50,-1,50,-1:18,50,-1:34,204,-1:5,98:2,204,98:3,207,9" +
"8:2,-1,98:5,207,98:3,-1:3,98:5,-1:3,98,-1:5,98:12,-1:2,98,1,51,75,95:61,-1:" +
"7,50,-1:5,98:2,50,98,94,98:4,-1,98:9,-1:3,98,94,98:3,-1:3,98,-1:5,98:12,-1:" +
"2,98,1,5:4,-1:2,5:57,-1:7,124,-1:5,56:2,124,56:2,122,56:3,-1,56:6,125,56:2," +
"-1:3,56:3,125,56,-1:3,56,-1:5,56:4,122,56:7,-1:2,56,-1:7,178,-1:7,178,211,1" +
"69,-1:18,169,-1:13,211,-1:20,192,-1:7,192,-1,192,-1:18,192,-1:40,98:2,-1,98" +
":6,-1,98:9,-1:3,203,98:4,-1:3,98,-1:5,98:12,-1:2,98,-1:7,190,-1:7,190,-1:13" +
",190,-1:8,190,-1:32,151,-1:5,56:2,151,56,146,56:4,-1,56:9,-1:3,56,146,56:3," +
"-1:3,56,-1:5,56:12,-1:2,56,-1:7,190,-1:5,56:2,190,56:6,-1,56:6,155,56:2,-1:" +
"3,56:3,155,56,-1:3,56,-1:5,56:12,-1:2,56,-1:7,196,-1:5,56:2,196,56:2,164,56" +
":3,-1,56:9,-1:3,56:5,-1:3,56,-1:5,56:4,164,56:7,-1:2,56,-1:7,192,-1:5,98:2," +
"192,98,197,98:4,-1,98:9,-1:3,98,197,98:3,-1:3,98,-1:5,98:12,-1:2,98,-1:7,19" +
"0,-1:5,98:2,190,98:6,-1,98:6,189,98:2,-1:3,98:3,189,98,-1:3,98,-1:5,98:12,-" +
"1:2,98,-1:7,220,-1:5,56,214,220,56,128,56:4,-1,56:9,-1:3,56,128,56:3,-1:3,5" +
"6,-1:5,56:6,214,56:5,-1:2,56,-1:7,182,-1:7,182,172,151,-1:18,151,-1:13,172," +
"-1:20,192,-1:5,56:2,192,56,161,56:4,-1,56:9,-1:3,56,161,56:3,-1:3,56,-1:5,5" +
"6:12,-1:2,56,-1:7,136,-1:5,56:2,136,56:2,131,56:3,-1,56:9,-1:3,56:5,-1:3,56" +
",-1:5,56:4,131,56:7,-1:2,56,-1:7,139,-1:5,56:2,139,56:6,-1,56:4,134,56:4,-1" +
":3,56:5,-1:3,56,-1:5,56:5,134,56:6,-1:2,56,-1:7,213,-1:5,56:2,213,56:6,-1,5" +
"6:6,215,56:2,-1:3,56:3,215,56,-1:3,56,-1:5,56:12,-1:2,56,-1:7,142,-1:5,56:2" +
",142,56:6,-1,56:4,137,56:4,-1:3,56:5,-1:3,56,-1:5,56:5,137,56:6,-1:2,56,-1:" +
"13,98:2,-1,98:6,-1,98:9,-1:3,98:3,212,98,-1:3,98,-1:5,98:12,-1:2,98,-1:13,9" +
"8:2,-1,98:6,-1,98:9,-1:3,98:2,226,98:2,-1:3,98,-1:5,98:12,-1:2,98");

	public java_cup.runtime.Symbol next_token ()
		throws java.io.IOException {
		int yy_lookahead;
		int yy_anchor = YY_NO_ANCHOR;
		int yy_state = yy_state_dtrans[yy_lexical_state];
		int yy_next_state = YY_NO_STATE;
		int yy_last_accept_state = YY_NO_STATE;
		boolean yy_initial = true;
		int yy_this_accept;

		yy_mark_start();
		yy_this_accept = yy_acpt[yy_state];
		if (YY_NOT_ACCEPT != yy_this_accept) {
			yy_last_accept_state = yy_state;
			yy_mark_end();
		}
		while (true) {
			if (yy_initial && yy_at_bol) yy_lookahead = YY_BOL;
			else yy_lookahead = yy_advance();
			yy_next_state = YY_F;
			yy_next_state = yy_nxt[yy_rmap[yy_state]][yy_cmap[yy_lookahead]];
			if (YY_EOF == yy_lookahead && true == yy_initial) {

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
	   case ESTOFERROR:
	   break;
    }
    return new Symbol(TokenConstants.EOF);
			}
			if (YY_F != yy_next_state) {
				yy_state = yy_next_state;
				yy_initial = false;
				yy_this_accept = yy_acpt[yy_state];
				if (YY_NOT_ACCEPT != yy_this_accept) {
					yy_last_accept_state = yy_state;
					yy_mark_end();
				}
			}
			else {
				if (YY_NO_STATE == yy_last_accept_state) {
					throw (new Error("Lexical Error: Unmatched Input."));
				}
				else {
					yy_anchor = yy_acpt[yy_last_accept_state];
					if (0 != (YY_END & yy_anchor)) {
						yy_move_end();
					}
					yy_to_mark();
					switch (yy_last_accept_state) {
					case 1:
						
					case -2:
						break;
					case 2:
						{return new Symbol(TokenConstants.LPAREN); }
					case -3:
						break;
					case 3:
						{return new Symbol(TokenConstants.MULT); }
					case -4:
						break;
					case 4:
						{return new Symbol(TokenConstants.RPAREN); }
					case -5:
						break;
					case 5:
						{ /* This rule should be the very last
                                     in your lexical specification and
                                     will match match everything not
                                     matched by other lexical rules. */
                                  System.err.println("LEXER BUG - UNMATCHED: " + yytext()); }
					case -6:
						break;
					case 6:
						{white++;}
					case -7:
						break;
					case 7:
						{curr_lineno++;}
					case -8:
						break;
					case 8:
						{return new Symbol(TokenConstants.MINUS); }
					case -9:
						break;
					case 9:
						{return new Symbol(TokenConstants.EQ); }
					case -10:
						break;
					case 10:
						{}
					case -11:
						break;
					case 11:
						{	AbstractSymbol tip = AbstractTable.idtable.addString(yytext());
							return new Symbol(TokenConstants.TYPEID,tip); }
					case -12:
						break;
					case 12:
						{	AbstractSymbol obj = AbstractTable.idtable.addString(yytext());
							return new Symbol(TokenConstants.OBJECTID,obj); }
					case -13:
						break;
					case 13:
						{return new Symbol(TokenConstants.DOT); }
					case -14:
						break;
					case 14:
						{return new Symbol(TokenConstants.AT); }
					case -15:
						break;
					case 15:
						{return new Symbol(TokenConstants.NEG); }
					case -16:
						break;
					case 16:
						{return new Symbol(TokenConstants.DIV); }
					case -17:
						break;
					case 17:
						{return new Symbol(TokenConstants.PLUS); }
					case -18:
						break;
					case 18:
						{return new Symbol(TokenConstants.LT); }
					case -19:
						break;
					case 19:
						{return new Symbol(TokenConstants.COMMA); }
					case -20:
						break;
					case 20:
						{return new Symbol(TokenConstants.COLON); }
					case -21:
						break;
					case 21:
						{return new Symbol(TokenConstants.SEMI); }
					case -22:
						break;
					case 22:
						{return new Symbol(TokenConstants.LBRACE); }
					case -23:
						break;
					case 23:
						{return new Symbol(TokenConstants.RBRACE); }
					case -24:
						break;
					case 24:
						{	AbstractSymbol inte = AbstractTable.inttable.addString(yytext());
							return new Symbol(TokenConstants.INT_CONST,inte); }
					case -25:
						break;
					case 25:
						{yybegin(COMMENT);comentario++;}
					case -26:
						break;
					case 26:
						{return new Symbol(TokenConstants.ERROR);}
					case -27:
						break;
					case 27:
						{return new Symbol(TokenConstants.FI);}
					case -28:
						break;
					case 28:
						{return new Symbol(TokenConstants.IF);}
					case -29:
						break;
					case 29:
						{return new Symbol(TokenConstants.IN);}
					case -30:
						break;
					case 30:
						{return new Symbol(TokenConstants.DARROW); }
					case -31:
						break;
					case 31:
						{return new Symbol(TokenConstants.OF);}
					case -32:
						break;
					case 32:
						{return new Symbol(TokenConstants.ASSIGN); }
					case -33:
						break;
					case 33:
						{return new Symbol(TokenConstants.LE); }
					case -34:
						break;
					case 34:
						{return new Symbol(TokenConstants.LET);}
					case -35:
						break;
					case 35:
						{return new Symbol(TokenConstants.NEW);}
					case -36:
						break;
					case 36:
						{}
					case -37:
						break;
					case 37:
						{return new Symbol(TokenConstants.NOT); }
					case -38:
						break;
					case 38:
						{
										String token = yytext().substring(1, yytext().length() - 1);
										int l = token.length();
										return new Symbol(TokenConstants.STR_CONST, new StringSymbol(token,l,0));
									 }
					case -39:
						break;
					case 39:
						{return new Symbol(TokenConstants.ELSE);}
					case -40:
						break;
					case 40:
						{return new Symbol(TokenConstants.ESAC);}
					case -41:
						break;
					case 41:
						{return new Symbol(TokenConstants.POOL);}
					case -42:
						break;
					case 42:
						{return new Symbol(TokenConstants.THEN);}
					case -43:
						break;
					case 43:
						{return new Symbol(TokenConstants.LOOP);}
					case -44:
						break;
					case 44:
						{return new Symbol(TokenConstants.CASE);}
					case -45:
						break;
					case 45:
						{return new Symbol(TokenConstants.BOOL_CONST, "true"); }
					case -46:
						break;
					case 46:
						{return new Symbol(TokenConstants.CLASS);}
					case -47:
						break;
					case 47:
						{return new Symbol(TokenConstants.WHILE);}
					case -48:
						break;
					case 48:
						{return new Symbol(TokenConstants.BOOL_CONST, "false"); }
					case -49:
						break;
					case 49:
						{return new Symbol(TokenConstants.ISVOID);}
					case -50:
						break;
					case 50:
						{return new Symbol(TokenConstants.INHERITS);}
					case -51:
						break;
					case 51:
						{}
					case -52:
						break;
					case 52:
						{comentario--; if (comentario == 0) {yybegin(YYINITIAL);}}
					case -53:
						break;
					case 54:
						{ /* This rule should be the very last
                                     in your lexical specification and
                                     will match match everything not
                                     matched by other lexical rules. */
                                  System.err.println("LEXER BUG - UNMATCHED: " + yytext()); }
					case -54:
						break;
					case 55:
						{white++;}
					case -55:
						break;
					case 56:
						{	AbstractSymbol tip = AbstractTable.idtable.addString(yytext());
							return new Symbol(TokenConstants.TYPEID,tip); }
					case -56:
						break;
					case 57:
						{	AbstractSymbol obj = AbstractTable.idtable.addString(yytext());
							return new Symbol(TokenConstants.OBJECTID,obj); }
					case -57:
						break;
					case 58:
						{return new Symbol(TokenConstants.FI);}
					case -58:
						break;
					case 59:
						{return new Symbol(TokenConstants.IF);}
					case -59:
						break;
					case 60:
						{return new Symbol(TokenConstants.IN);}
					case -60:
						break;
					case 61:
						{return new Symbol(TokenConstants.OF);}
					case -61:
						break;
					case 62:
						{return new Symbol(TokenConstants.LET);}
					case -62:
						break;
					case 63:
						{return new Symbol(TokenConstants.NEW);}
					case -63:
						break;
					case 64:
						{return new Symbol(TokenConstants.ELSE);}
					case -64:
						break;
					case 65:
						{return new Symbol(TokenConstants.ESAC);}
					case -65:
						break;
					case 66:
						{return new Symbol(TokenConstants.POOL);}
					case -66:
						break;
					case 67:
						{return new Symbol(TokenConstants.THEN);}
					case -67:
						break;
					case 68:
						{return new Symbol(TokenConstants.LOOP);}
					case -68:
						break;
					case 69:
						{return new Symbol(TokenConstants.CASE);}
					case -69:
						break;
					case 70:
						{return new Symbol(TokenConstants.BOOL_CONST, "true"); }
					case -70:
						break;
					case 71:
						{return new Symbol(TokenConstants.CLASS);}
					case -71:
						break;
					case 72:
						{return new Symbol(TokenConstants.WHILE);}
					case -72:
						break;
					case 73:
						{return new Symbol(TokenConstants.BOOL_CONST, "false"); }
					case -73:
						break;
					case 74:
						{return new Symbol(TokenConstants.INHERITS);}
					case -74:
						break;
					case 75:
						{}
					case -75:
						break;
					case 77:
						{ /* This rule should be the very last
                                     in your lexical specification and
                                     will match match everything not
                                     matched by other lexical rules. */
                                  System.err.println("LEXER BUG - UNMATCHED: " + yytext()); }
					case -76:
						break;
					case 78:
						{	AbstractSymbol tip = AbstractTable.idtable.addString(yytext());
							return new Symbol(TokenConstants.TYPEID,tip); }
					case -77:
						break;
					case 79:
						{	AbstractSymbol obj = AbstractTable.idtable.addString(yytext());
							return new Symbol(TokenConstants.OBJECTID,obj); }
					case -78:
						break;
					case 80:
						{return new Symbol(TokenConstants.FI);}
					case -79:
						break;
					case 81:
						{return new Symbol(TokenConstants.IF);}
					case -80:
						break;
					case 82:
						{return new Symbol(TokenConstants.IN);}
					case -81:
						break;
					case 83:
						{return new Symbol(TokenConstants.OF);}
					case -82:
						break;
					case 84:
						{return new Symbol(TokenConstants.LET);}
					case -83:
						break;
					case 85:
						{return new Symbol(TokenConstants.NEW);}
					case -84:
						break;
					case 86:
						{return new Symbol(TokenConstants.ELSE);}
					case -85:
						break;
					case 87:
						{return new Symbol(TokenConstants.ESAC);}
					case -86:
						break;
					case 88:
						{return new Symbol(TokenConstants.POOL);}
					case -87:
						break;
					case 89:
						{return new Symbol(TokenConstants.THEN);}
					case -88:
						break;
					case 90:
						{return new Symbol(TokenConstants.LOOP);}
					case -89:
						break;
					case 91:
						{return new Symbol(TokenConstants.CASE);}
					case -90:
						break;
					case 92:
						{return new Symbol(TokenConstants.CLASS);}
					case -91:
						break;
					case 93:
						{return new Symbol(TokenConstants.WHILE);}
					case -92:
						break;
					case 94:
						{return new Symbol(TokenConstants.INHERITS);}
					case -93:
						break;
					case 95:
						{}
					case -94:
						break;
					case 97:
						{	AbstractSymbol tip = AbstractTable.idtable.addString(yytext());
							return new Symbol(TokenConstants.TYPEID,tip); }
					case -95:
						break;
					case 98:
						{	AbstractSymbol obj = AbstractTable.idtable.addString(yytext());
							return new Symbol(TokenConstants.OBJECTID,obj); }
					case -96:
						break;
					case 99:
						{return new Symbol(TokenConstants.FI);}
					case -97:
						break;
					case 100:
						{return new Symbol(TokenConstants.IF);}
					case -98:
						break;
					case 101:
						{return new Symbol(TokenConstants.LET);}
					case -99:
						break;
					case 102:
						{return new Symbol(TokenConstants.ELSE);}
					case -100:
						break;
					case 103:
						{return new Symbol(TokenConstants.ESAC);}
					case -101:
						break;
					case 104:
						{return new Symbol(TokenConstants.POOL);}
					case -102:
						break;
					case 105:
						{return new Symbol(TokenConstants.THEN);}
					case -103:
						break;
					case 106:
						{return new Symbol(TokenConstants.CASE);}
					case -104:
						break;
					case 107:
						{return new Symbol(TokenConstants.CLASS);}
					case -105:
						break;
					case 109:
						{	AbstractSymbol tip = AbstractTable.idtable.addString(yytext());
							return new Symbol(TokenConstants.TYPEID,tip); }
					case -106:
						break;
					case 110:
						{	AbstractSymbol obj = AbstractTable.idtable.addString(yytext());
							return new Symbol(TokenConstants.OBJECTID,obj); }
					case -107:
						break;
					case 111:
						{return new Symbol(TokenConstants.FI);}
					case -108:
						break;
					case 112:
						{return new Symbol(TokenConstants.LET);}
					case -109:
						break;
					case 113:
						{return new Symbol(TokenConstants.ELSE);}
					case -110:
						break;
					case 115:
						{	AbstractSymbol tip = AbstractTable.idtable.addString(yytext());
							return new Symbol(TokenConstants.TYPEID,tip); }
					case -111:
						break;
					case 116:
						{	AbstractSymbol obj = AbstractTable.idtable.addString(yytext());
							return new Symbol(TokenConstants.OBJECTID,obj); }
					case -112:
						break;
					case 117:
						{return new Symbol(TokenConstants.ELSE);}
					case -113:
						break;
					case 119:
						{	AbstractSymbol tip = AbstractTable.idtable.addString(yytext());
							return new Symbol(TokenConstants.TYPEID,tip); }
					case -114:
						break;
					case 120:
						{	AbstractSymbol obj = AbstractTable.idtable.addString(yytext());
							return new Symbol(TokenConstants.OBJECTID,obj); }
					case -115:
						break;
					case 122:
						{	AbstractSymbol tip = AbstractTable.idtable.addString(yytext());
							return new Symbol(TokenConstants.TYPEID,tip); }
					case -116:
						break;
					case 123:
						{	AbstractSymbol obj = AbstractTable.idtable.addString(yytext());
							return new Symbol(TokenConstants.OBJECTID,obj); }
					case -117:
						break;
					case 125:
						{	AbstractSymbol tip = AbstractTable.idtable.addString(yytext());
							return new Symbol(TokenConstants.TYPEID,tip); }
					case -118:
						break;
					case 126:
						{	AbstractSymbol obj = AbstractTable.idtable.addString(yytext());
							return new Symbol(TokenConstants.OBJECTID,obj); }
					case -119:
						break;
					case 128:
						{	AbstractSymbol tip = AbstractTable.idtable.addString(yytext());
							return new Symbol(TokenConstants.TYPEID,tip); }
					case -120:
						break;
					case 129:
						{	AbstractSymbol obj = AbstractTable.idtable.addString(yytext());
							return new Symbol(TokenConstants.OBJECTID,obj); }
					case -121:
						break;
					case 131:
						{	AbstractSymbol tip = AbstractTable.idtable.addString(yytext());
							return new Symbol(TokenConstants.TYPEID,tip); }
					case -122:
						break;
					case 132:
						{	AbstractSymbol obj = AbstractTable.idtable.addString(yytext());
							return new Symbol(TokenConstants.OBJECTID,obj); }
					case -123:
						break;
					case 134:
						{	AbstractSymbol tip = AbstractTable.idtable.addString(yytext());
							return new Symbol(TokenConstants.TYPEID,tip); }
					case -124:
						break;
					case 135:
						{	AbstractSymbol obj = AbstractTable.idtable.addString(yytext());
							return new Symbol(TokenConstants.OBJECTID,obj); }
					case -125:
						break;
					case 137:
						{	AbstractSymbol tip = AbstractTable.idtable.addString(yytext());
							return new Symbol(TokenConstants.TYPEID,tip); }
					case -126:
						break;
					case 138:
						{	AbstractSymbol obj = AbstractTable.idtable.addString(yytext());
							return new Symbol(TokenConstants.OBJECTID,obj); }
					case -127:
						break;
					case 140:
						{	AbstractSymbol tip = AbstractTable.idtable.addString(yytext());
							return new Symbol(TokenConstants.TYPEID,tip); }
					case -128:
						break;
					case 141:
						{	AbstractSymbol obj = AbstractTable.idtable.addString(yytext());
							return new Symbol(TokenConstants.OBJECTID,obj); }
					case -129:
						break;
					case 143:
						{	AbstractSymbol tip = AbstractTable.idtable.addString(yytext());
							return new Symbol(TokenConstants.TYPEID,tip); }
					case -130:
						break;
					case 144:
						{	AbstractSymbol obj = AbstractTable.idtable.addString(yytext());
							return new Symbol(TokenConstants.OBJECTID,obj); }
					case -131:
						break;
					case 146:
						{	AbstractSymbol tip = AbstractTable.idtable.addString(yytext());
							return new Symbol(TokenConstants.TYPEID,tip); }
					case -132:
						break;
					case 147:
						{	AbstractSymbol obj = AbstractTable.idtable.addString(yytext());
							return new Symbol(TokenConstants.OBJECTID,obj); }
					case -133:
						break;
					case 149:
						{	AbstractSymbol tip = AbstractTable.idtable.addString(yytext());
							return new Symbol(TokenConstants.TYPEID,tip); }
					case -134:
						break;
					case 150:
						{	AbstractSymbol obj = AbstractTable.idtable.addString(yytext());
							return new Symbol(TokenConstants.OBJECTID,obj); }
					case -135:
						break;
					case 152:
						{	AbstractSymbol tip = AbstractTable.idtable.addString(yytext());
							return new Symbol(TokenConstants.TYPEID,tip); }
					case -136:
						break;
					case 153:
						{	AbstractSymbol obj = AbstractTable.idtable.addString(yytext());
							return new Symbol(TokenConstants.OBJECTID,obj); }
					case -137:
						break;
					case 155:
						{	AbstractSymbol tip = AbstractTable.idtable.addString(yytext());
							return new Symbol(TokenConstants.TYPEID,tip); }
					case -138:
						break;
					case 156:
						{	AbstractSymbol obj = AbstractTable.idtable.addString(yytext());
							return new Symbol(TokenConstants.OBJECTID,obj); }
					case -139:
						break;
					case 158:
						{	AbstractSymbol tip = AbstractTable.idtable.addString(yytext());
							return new Symbol(TokenConstants.TYPEID,tip); }
					case -140:
						break;
					case 159:
						{	AbstractSymbol obj = AbstractTable.idtable.addString(yytext());
							return new Symbol(TokenConstants.OBJECTID,obj); }
					case -141:
						break;
					case 161:
						{	AbstractSymbol tip = AbstractTable.idtable.addString(yytext());
							return new Symbol(TokenConstants.TYPEID,tip); }
					case -142:
						break;
					case 162:
						{	AbstractSymbol obj = AbstractTable.idtable.addString(yytext());
							return new Symbol(TokenConstants.OBJECTID,obj); }
					case -143:
						break;
					case 164:
						{	AbstractSymbol tip = AbstractTable.idtable.addString(yytext());
							return new Symbol(TokenConstants.TYPEID,tip); }
					case -144:
						break;
					case 165:
						{	AbstractSymbol obj = AbstractTable.idtable.addString(yytext());
							return new Symbol(TokenConstants.OBJECTID,obj); }
					case -145:
						break;
					case 167:
						{	AbstractSymbol tip = AbstractTable.idtable.addString(yytext());
							return new Symbol(TokenConstants.TYPEID,tip); }
					case -146:
						break;
					case 168:
						{	AbstractSymbol obj = AbstractTable.idtable.addString(yytext());
							return new Symbol(TokenConstants.OBJECTID,obj); }
					case -147:
						break;
					case 170:
						{	AbstractSymbol tip = AbstractTable.idtable.addString(yytext());
							return new Symbol(TokenConstants.TYPEID,tip); }
					case -148:
						break;
					case 171:
						{	AbstractSymbol obj = AbstractTable.idtable.addString(yytext());
							return new Symbol(TokenConstants.OBJECTID,obj); }
					case -149:
						break;
					case 173:
						{	AbstractSymbol tip = AbstractTable.idtable.addString(yytext());
							return new Symbol(TokenConstants.TYPEID,tip); }
					case -150:
						break;
					case 174:
						{	AbstractSymbol obj = AbstractTable.idtable.addString(yytext());
							return new Symbol(TokenConstants.OBJECTID,obj); }
					case -151:
						break;
					case 176:
						{	AbstractSymbol tip = AbstractTable.idtable.addString(yytext());
							return new Symbol(TokenConstants.TYPEID,tip); }
					case -152:
						break;
					case 177:
						{	AbstractSymbol obj = AbstractTable.idtable.addString(yytext());
							return new Symbol(TokenConstants.OBJECTID,obj); }
					case -153:
						break;
					case 179:
						{	AbstractSymbol obj = AbstractTable.idtable.addString(yytext());
							return new Symbol(TokenConstants.OBJECTID,obj); }
					case -154:
						break;
					case 181:
						{	AbstractSymbol obj = AbstractTable.idtable.addString(yytext());
							return new Symbol(TokenConstants.OBJECTID,obj); }
					case -155:
						break;
					case 183:
						{	AbstractSymbol obj = AbstractTable.idtable.addString(yytext());
							return new Symbol(TokenConstants.OBJECTID,obj); }
					case -156:
						break;
					case 185:
						{	AbstractSymbol obj = AbstractTable.idtable.addString(yytext());
							return new Symbol(TokenConstants.OBJECTID,obj); }
					case -157:
						break;
					case 187:
						{	AbstractSymbol obj = AbstractTable.idtable.addString(yytext());
							return new Symbol(TokenConstants.OBJECTID,obj); }
					case -158:
						break;
					case 189:
						{	AbstractSymbol obj = AbstractTable.idtable.addString(yytext());
							return new Symbol(TokenConstants.OBJECTID,obj); }
					case -159:
						break;
					case 191:
						{	AbstractSymbol obj = AbstractTable.idtable.addString(yytext());
							return new Symbol(TokenConstants.OBJECTID,obj); }
					case -160:
						break;
					case 193:
						{	AbstractSymbol obj = AbstractTable.idtable.addString(yytext());
							return new Symbol(TokenConstants.OBJECTID,obj); }
					case -161:
						break;
					case 195:
						{	AbstractSymbol obj = AbstractTable.idtable.addString(yytext());
							return new Symbol(TokenConstants.OBJECTID,obj); }
					case -162:
						break;
					case 197:
						{	AbstractSymbol obj = AbstractTable.idtable.addString(yytext());
							return new Symbol(TokenConstants.OBJECTID,obj); }
					case -163:
						break;
					case 199:
						{	AbstractSymbol obj = AbstractTable.idtable.addString(yytext());
							return new Symbol(TokenConstants.OBJECTID,obj); }
					case -164:
						break;
					case 201:
						{	AbstractSymbol obj = AbstractTable.idtable.addString(yytext());
							return new Symbol(TokenConstants.OBJECTID,obj); }
					case -165:
						break;
					case 203:
						{	AbstractSymbol obj = AbstractTable.idtable.addString(yytext());
							return new Symbol(TokenConstants.OBJECTID,obj); }
					case -166:
						break;
					case 205:
						{	AbstractSymbol obj = AbstractTable.idtable.addString(yytext());
							return new Symbol(TokenConstants.OBJECTID,obj); }
					case -167:
						break;
					case 207:
						{	AbstractSymbol obj = AbstractTable.idtable.addString(yytext());
							return new Symbol(TokenConstants.OBJECTID,obj); }
					case -168:
						break;
					case 209:
						{	AbstractSymbol tip = AbstractTable.idtable.addString(yytext());
							return new Symbol(TokenConstants.TYPEID,tip); }
					case -169:
						break;
					case 212:
						{	AbstractSymbol obj = AbstractTable.idtable.addString(yytext());
							return new Symbol(TokenConstants.OBJECTID,obj); }
					case -170:
						break;
					case 214:
						{	AbstractSymbol tip = AbstractTable.idtable.addString(yytext());
							return new Symbol(TokenConstants.TYPEID,tip); }
					case -171:
						break;
					case 215:
						{	AbstractSymbol tip = AbstractTable.idtable.addString(yytext());
							return new Symbol(TokenConstants.TYPEID,tip); }
					case -172:
						break;
					case 216:
						{	AbstractSymbol tip = AbstractTable.idtable.addString(yytext());
							return new Symbol(TokenConstants.TYPEID,tip); }
					case -173:
						break;
					case 217:
						{	AbstractSymbol obj = AbstractTable.idtable.addString(yytext());
							return new Symbol(TokenConstants.OBJECTID,obj); }
					case -174:
						break;
					case 218:
						{	AbstractSymbol obj = AbstractTable.idtable.addString(yytext());
							return new Symbol(TokenConstants.OBJECTID,obj); }
					case -175:
						break;
					case 219:
						{	AbstractSymbol tip = AbstractTable.idtable.addString(yytext());
							return new Symbol(TokenConstants.TYPEID,tip); }
					case -176:
						break;
					case 221:
						{	AbstractSymbol tip = AbstractTable.idtable.addString(yytext());
							return new Symbol(TokenConstants.TYPEID,tip); }
					case -177:
						break;
					case 222:
						{	AbstractSymbol tip = AbstractTable.idtable.addString(yytext());
							return new Symbol(TokenConstants.TYPEID,tip); }
					case -178:
						break;
					case 223:
						{	AbstractSymbol tip = AbstractTable.idtable.addString(yytext());
							return new Symbol(TokenConstants.TYPEID,tip); }
					case -179:
						break;
					case 224:
						{	AbstractSymbol tip = AbstractTable.idtable.addString(yytext());
							return new Symbol(TokenConstants.TYPEID,tip); }
					case -180:
						break;
					case 225:
						{	AbstractSymbol tip = AbstractTable.idtable.addString(yytext());
							return new Symbol(TokenConstants.TYPEID,tip); }
					case -181:
						break;
					case 226:
						{	AbstractSymbol obj = AbstractTable.idtable.addString(yytext());
							return new Symbol(TokenConstants.OBJECTID,obj); }
					case -182:
						break;
					case 227:
						{	AbstractSymbol obj = AbstractTable.idtable.addString(yytext());
							return new Symbol(TokenConstants.OBJECTID,obj); }
					case -183:
						break;
					default:
						yy_error(YY_E_INTERNAL,false);
					case -1:
					}
					yy_initial = true;
					yy_state = yy_state_dtrans[yy_lexical_state];
					yy_next_state = YY_NO_STATE;
					yy_last_accept_state = YY_NO_STATE;
					yy_mark_start();
					yy_this_accept = yy_acpt[yy_state];
					if (YY_NOT_ACCEPT != yy_this_accept) {
						yy_last_accept_state = yy_state;
						yy_mark_end();
					}
				}
			}
		}
	}
}
