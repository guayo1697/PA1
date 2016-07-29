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
	private final int ESTOFERROR = 3;
	private final int YYINITIAL = 0;
	private final int COMMENT = 1;
	private final int STR = 4;
	private final int COMMENTLINEAL = 2;
	private final int yy_state_dtrans[] = {
		0,
		237,
		238,
		239,
		240
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
		/* 53 */ YY_NO_ANCHOR,
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
		/* 68 */ YY_NOT_ACCEPT,
		/* 69 */ YY_NO_ANCHOR,
		/* 70 */ YY_NO_ANCHOR,
		/* 71 */ YY_NO_ANCHOR,
		/* 72 */ YY_NO_ANCHOR,
		/* 73 */ YY_NO_ANCHOR,
		/* 74 */ YY_NO_ANCHOR,
		/* 75 */ YY_NO_ANCHOR,
		/* 76 */ YY_NO_ANCHOR,
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
		/* 92 */ YY_NOT_ACCEPT,
		/* 93 */ YY_NO_ANCHOR,
		/* 94 */ YY_NO_ANCHOR,
		/* 95 */ YY_NO_ANCHOR,
		/* 96 */ YY_NO_ANCHOR,
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
		/* 108 */ YY_NO_ANCHOR,
		/* 109 */ YY_NO_ANCHOR,
		/* 110 */ YY_NO_ANCHOR,
		/* 111 */ YY_NO_ANCHOR,
		/* 112 */ YY_NO_ANCHOR,
		/* 113 */ YY_NOT_ACCEPT,
		/* 114 */ YY_NO_ANCHOR,
		/* 115 */ YY_NO_ANCHOR,
		/* 116 */ YY_NO_ANCHOR,
		/* 117 */ YY_NO_ANCHOR,
		/* 118 */ YY_NO_ANCHOR,
		/* 119 */ YY_NO_ANCHOR,
		/* 120 */ YY_NO_ANCHOR,
		/* 121 */ YY_NO_ANCHOR,
		/* 122 */ YY_NO_ANCHOR,
		/* 123 */ YY_NO_ANCHOR,
		/* 124 */ YY_NO_ANCHOR,
		/* 125 */ YY_NO_ANCHOR,
		/* 126 */ YY_NO_ANCHOR,
		/* 127 */ YY_NOT_ACCEPT,
		/* 128 */ YY_NO_ANCHOR,
		/* 129 */ YY_NO_ANCHOR,
		/* 130 */ YY_NO_ANCHOR,
		/* 131 */ YY_NO_ANCHOR,
		/* 132 */ YY_NO_ANCHOR,
		/* 133 */ YY_NO_ANCHOR,
		/* 134 */ YY_NOT_ACCEPT,
		/* 135 */ YY_NO_ANCHOR,
		/* 136 */ YY_NO_ANCHOR,
		/* 137 */ YY_NO_ANCHOR,
		/* 138 */ YY_NOT_ACCEPT,
		/* 139 */ YY_NO_ANCHOR,
		/* 140 */ YY_NO_ANCHOR,
		/* 141 */ YY_NOT_ACCEPT,
		/* 142 */ YY_NO_ANCHOR,
		/* 143 */ YY_NO_ANCHOR,
		/* 144 */ YY_NOT_ACCEPT,
		/* 145 */ YY_NO_ANCHOR,
		/* 146 */ YY_NO_ANCHOR,
		/* 147 */ YY_NOT_ACCEPT,
		/* 148 */ YY_NO_ANCHOR,
		/* 149 */ YY_NO_ANCHOR,
		/* 150 */ YY_NOT_ACCEPT,
		/* 151 */ YY_NO_ANCHOR,
		/* 152 */ YY_NO_ANCHOR,
		/* 153 */ YY_NOT_ACCEPT,
		/* 154 */ YY_NO_ANCHOR,
		/* 155 */ YY_NO_ANCHOR,
		/* 156 */ YY_NOT_ACCEPT,
		/* 157 */ YY_NO_ANCHOR,
		/* 158 */ YY_NO_ANCHOR,
		/* 159 */ YY_NOT_ACCEPT,
		/* 160 */ YY_NO_ANCHOR,
		/* 161 */ YY_NO_ANCHOR,
		/* 162 */ YY_NOT_ACCEPT,
		/* 163 */ YY_NO_ANCHOR,
		/* 164 */ YY_NO_ANCHOR,
		/* 165 */ YY_NOT_ACCEPT,
		/* 166 */ YY_NO_ANCHOR,
		/* 167 */ YY_NO_ANCHOR,
		/* 168 */ YY_NOT_ACCEPT,
		/* 169 */ YY_NO_ANCHOR,
		/* 170 */ YY_NO_ANCHOR,
		/* 171 */ YY_NOT_ACCEPT,
		/* 172 */ YY_NO_ANCHOR,
		/* 173 */ YY_NO_ANCHOR,
		/* 174 */ YY_NOT_ACCEPT,
		/* 175 */ YY_NO_ANCHOR,
		/* 176 */ YY_NO_ANCHOR,
		/* 177 */ YY_NOT_ACCEPT,
		/* 178 */ YY_NO_ANCHOR,
		/* 179 */ YY_NO_ANCHOR,
		/* 180 */ YY_NOT_ACCEPT,
		/* 181 */ YY_NO_ANCHOR,
		/* 182 */ YY_NO_ANCHOR,
		/* 183 */ YY_NOT_ACCEPT,
		/* 184 */ YY_NO_ANCHOR,
		/* 185 */ YY_NO_ANCHOR,
		/* 186 */ YY_NOT_ACCEPT,
		/* 187 */ YY_NO_ANCHOR,
		/* 188 */ YY_NO_ANCHOR,
		/* 189 */ YY_NOT_ACCEPT,
		/* 190 */ YY_NO_ANCHOR,
		/* 191 */ YY_NO_ANCHOR,
		/* 192 */ YY_NOT_ACCEPT,
		/* 193 */ YY_NO_ANCHOR,
		/* 194 */ YY_NO_ANCHOR,
		/* 195 */ YY_NOT_ACCEPT,
		/* 196 */ YY_NO_ANCHOR,
		/* 197 */ YY_NO_ANCHOR,
		/* 198 */ YY_NOT_ACCEPT,
		/* 199 */ YY_NO_ANCHOR,
		/* 200 */ YY_NO_ANCHOR,
		/* 201 */ YY_NOT_ACCEPT,
		/* 202 */ YY_NO_ANCHOR,
		/* 203 */ YY_NO_ANCHOR,
		/* 204 */ YY_NOT_ACCEPT,
		/* 205 */ YY_NO_ANCHOR,
		/* 206 */ YY_NO_ANCHOR,
		/* 207 */ YY_NOT_ACCEPT,
		/* 208 */ YY_NO_ANCHOR,
		/* 209 */ YY_NO_ANCHOR,
		/* 210 */ YY_NOT_ACCEPT,
		/* 211 */ YY_NO_ANCHOR,
		/* 212 */ YY_NO_ANCHOR,
		/* 213 */ YY_NOT_ACCEPT,
		/* 214 */ YY_NO_ANCHOR,
		/* 215 */ YY_NOT_ACCEPT,
		/* 216 */ YY_NO_ANCHOR,
		/* 217 */ YY_NOT_ACCEPT,
		/* 218 */ YY_NO_ANCHOR,
		/* 219 */ YY_NOT_ACCEPT,
		/* 220 */ YY_NO_ANCHOR,
		/* 221 */ YY_NOT_ACCEPT,
		/* 222 */ YY_NO_ANCHOR,
		/* 223 */ YY_NOT_ACCEPT,
		/* 224 */ YY_NO_ANCHOR,
		/* 225 */ YY_NOT_ACCEPT,
		/* 226 */ YY_NO_ANCHOR,
		/* 227 */ YY_NOT_ACCEPT,
		/* 228 */ YY_NO_ANCHOR,
		/* 229 */ YY_NOT_ACCEPT,
		/* 230 */ YY_NO_ANCHOR,
		/* 231 */ YY_NOT_ACCEPT,
		/* 232 */ YY_NOT_ACCEPT,
		/* 233 */ YY_NOT_ACCEPT,
		/* 234 */ YY_NOT_ACCEPT,
		/* 235 */ YY_NOT_ACCEPT,
		/* 236 */ YY_NOT_ACCEPT,
		/* 237 */ YY_NOT_ACCEPT,
		/* 238 */ YY_NOT_ACCEPT,
		/* 239 */ YY_NOT_ACCEPT,
		/* 240 */ YY_NOT_ACCEPT,
		/* 241 */ YY_NOT_ACCEPT,
		/* 242 */ YY_NO_ANCHOR,
		/* 243 */ YY_NOT_ACCEPT,
		/* 244 */ YY_NO_ANCHOR,
		/* 245 */ YY_NO_ANCHOR,
		/* 246 */ YY_NO_ANCHOR,
		/* 247 */ YY_NO_ANCHOR,
		/* 248 */ YY_NO_ANCHOR,
		/* 249 */ YY_NO_ANCHOR,
		/* 250 */ YY_NO_ANCHOR,
		/* 251 */ YY_NOT_ACCEPT,
		/* 252 */ YY_NO_ANCHOR,
		/* 253 */ YY_NOT_ACCEPT,
		/* 254 */ YY_NO_ANCHOR,
		/* 255 */ YY_NOT_ACCEPT,
		/* 256 */ YY_NO_ANCHOR,
		/* 257 */ YY_NO_ANCHOR,
		/* 258 */ YY_NO_ANCHOR,
		/* 259 */ YY_NO_ANCHOR,
		/* 260 */ YY_NO_ANCHOR,
		/* 261 */ YY_NO_ANCHOR
	};
	private int yy_cmap[] = unpackFromString(1,130,
"6,67:7,13,8,4,67,10,9,67:18,7,67,65,67:5,2,5,3,39,41,1,35,38,47:10,42,43,40" +
",11,12,67,36,15,64,14,32,18,25,64,28,26,64:2,19,64,27,31,33,64,23,17,29,24," +
"30,34,64:3,67,21,67:2,46,67,48,66,50,51,52,20,49,53,54,49:2,55,49,56,57,58," +
"49,59,60,22,61,62,63,49:3,44,16,45,37,67,0:2")[0];

	private int yy_rmap[] = unpackFromString(1,262,
"0,1,2,3,4,1:4,5,1,6,7,1:5,8,1:6,9,1:5,10,1,11,1:3,12,1,13,1:2,14,1:2,15,1:4" +
",16,1:5,17,1,18,1:9,19,20,21,22,1,23,24,21,1,21,1,21,15,1,14,21,1,25,21:2,2" +
"5,1,21,3,26,27,25,28,21,29,25,30,25,21,15,31,21,25:2,21,32,25,21,25,33,34,3" +
"5,36,25:2,21,25,37,21,25,21,25:3,38,39,40,21,25,41,25,42,43,44,25,45,46,47," +
"48,49,50,51,52,53,28,54,55,56,57,58,59,60,61,62,63,64,65,66,67,68,69,70,71," +
"72,73,74,75,76,77,78,79,80,81,82,83,84,85,86,87,88,89,90,91,92,93,94,13,95," +
"96,97,98,99,100,101,102,103,104,105,106,107,108,109,110,111,112,113,114,30," +
"115,116,117,118,119,120,121,122,123,124,125,126,15,127,31,128,14,129,41,130" +
",131,132,133,32,16,134,135,136,137,138,139,140,141,142,143,144,145,146,147," +
"148,149,150,151,152,153,154,155,156,157,158,159")[0];

	private int yy_nxt[][] = unpackFromString(160,68,
"1,2,3,4,5,6,7,8:2,68,7,9,7,10,11,70,69,70,242,254,12,7,71,70:2,93,114,257,7" +
"0,259,70,128,70,260,261,13,14,15,16,17,18,19,20,21,22,23,24,25,94:2,115,94," +
"129,94,136,140,246,143,146,94:4,149,70,26,94,7,-1:69,27,-1:69,28,-1:69,29,-" +
"1:74,30,-1:69,70,135,92,70:2,139,70,-1,70:13,-1:11,70:2,135,70:6,139,70:9,-" +
"1,70,-1:15,94,152,95,94:4,147,94:4,116,94:8,-1:11,94:2,152,94:5,116,94:10,-" +
"1,94,-1:2,35,-1:9,36,-1:103,25,-1:35,177,37,168,180,-1:3,76,-1:3,183,-1,186" +
",76,251,189,-1:2,38,-1:13,177,-1:3,180,186,183,-1:2,189,-1:2,168,-1,251,38," +
"-1:20,186,-1:11,186,-1:24,186,-1:28,41,-1,120,223,132,42,-1:7,43,-1:3,225,-" +
"1,44,-1:16,41,-1,132,-1:2,42,43,225,44,-1,223,-1:23,44,-1:2,81,-1:13,44,-1:" +
"21,81,-1:2,44,-1:25,48,-1,48,-1:33,48,-1:31,47:2,-1:42,47,-1:23,236,-1:5,23" +
"6,-1:6,236,-1:39,56:3,-1,56,-1,56:14,-1,56:43,-1,56:2,-1:4,60,-1:2,61,-1:12" +
",62,241,63,-1:33,64,-1:8,65,66,-1:11,8,-1:72,113,31,127,134,243,32,-1:4,32," +
"72,33,138,-1:2,141,-1:16,113,-1:3,134,138,72,243,33,141,-1:2,127,-1:21,70:2" +
",-1,70:4,-1,70:13,-1:11,70:19,-1,70,-1:15,94:2,150,94:4,153,94,155,94:4,158" +
",94:6,-1:11,94:7,158,94:5,155,94:5,-1,94,-1:17,219,-1:11,186,-1,251,-1:22,1" +
"86,-1:8,251,-1:19,70:2,186,70:4,-1,70:6,249,70:6,-1:11,70:7,249,70:11,-1,70" +
",-1:15,94:2,-1,94:4,-1,94:13,-1:11,94:19,-1,94,-1:16,171,174,168,-1:30,171," +
"-1:11,168,-1:21,70:2,72,70:4,-1,70:4,130,70:8,-1:11,70:8,130,70:10,-1,70,-1" +
":17,213,-1:2,213,-1,213,-1:33,213,-1:26,94:2,186,94:4,-1,94:6,203,94:6,-1:1" +
"1,94:7,203,94:11,-1,94,-1:17,44,-1:16,44,-1:24,44,-1:25,233,-1:9,233,-1:27," +
"233,-1:29,50,-1:9,235,-1:5,89,-1:18,89,-1:2,235,-1:18,54,-1:78,168:2,-1:42," +
"168,-1:21,70:2,73,148,70:2,96,-1,70:3,96,70,74,70:7,-1:11,70:10,74,70:3,148" +
",70:4,-1,70,-1:15,94,161,92,94:2,164,94,-1,94:13,-1:11,94:2,161,94:6,164,94" +
":9,-1,94,-1:17,108,47,48,-1:4,253,-1:2,233,-1:25,48,-1,233,-1:4,253,47,-1:2" +
"2,192,195,-1:13,251,-1:17,192,-1:13,251,-1:19,70:2,34,70:3,75,-1,70:3,75,70" +
":9,-1:11,70:19,-1,70,-1:15,94:2,255,252,94,250,94,-1,94:13,-1:11,94:9,250,9" +
"4:4,252,94:4,-1,94,-1:17,253,-1:6,253,-1:35,253,-1:24,76,-1:5,76,-1:6,76,-1" +
":4,38,-1:28,38,-1:18,70:2,168,163,70:3,-1,70:13,-1:11,70:14,163,70:4,-1,70," +
"-1:15,94:2,73,167,94:2,117,-1,94:3,117,94,97,94:7,-1:11,94:10,97,94:3,167,9" +
"4:4,-1,94,-1:17,204,-1,180,-1:7,183,-1:25,180,-1,183,-1:27,70,166,171,70:4," +
"-1,70:13,-1:11,70:2,166,70:16,-1,70,-1:15,94:2,144,94,170,94:2,-1,94:9,248," +
"94:3,-1:11,94:6,170,94:4,248,94:7,-1,94,-1:17,39,-1:5,78,-1:6,78,-1,189,-1:" +
"25,189,-1:24,70:2,76,70:4,-1,118,70:6,118,70:5,-1:11,70:19,-1,70,-1:15,94:2" +
",34,94:3,98,-1,94:3,98,94:9,-1:11,94:19,-1,94,-1:17,99,-1:5,76,-1:6,76,-1,2" +
"10,-1:25,210,-1:24,70:2,210,70:4,-1,70:9,175,70:3,-1:11,70:11,175,70:7,-1,7" +
"0,-1:15,94:2,162,94:4,-1,94:9,256,94:3,-1:11,94:11,256,94:7,-1,94,-1:15,70:" +
"2,251,70:4,-1,70:8,178,70:4,-1:11,70:16,178,70:2,-1,70,-1:15,94:2,165,94:4," +
"-1,94:6,179,94:6,-1:11,94:7,179,94:11,-1,94,-1:17,215,-1,180,-1:2,217,-1:2," +
"217,-1:27,180,-1:8,217,-1:20,70:2,38,70:4,-1,70:12,77,-1:11,70:17,77,70,-1," +
"70,-1:15,94:2,213,94:2,182,94,213,94:13,-1:11,94:9,182,94:9,-1,94,-1:17,217" +
",-1:4,217,-1:2,217,-1:36,217,-1:20,70:2,78,70:4,-1,101,70:6,101,70:5,-1:11," +
"70:19,-1,70,-1:15,94:2,217,94:4,217,94:2,185,94:10,-1:11,94:15,185,94:3,-1," +
"94,-1:17,38,-1:5,78,-1:6,78,-1:4,38,-1:28,38,-1:18,70:2,180,70,181,70:2,-1," +
"70:13,-1:11,70:6,181,70:12,-1,70,-1:15,94:2,180,94,188,94:2,-1,94:13,-1:11," +
"94:6,188,94:12,-1,94,-1:17,180,-1,180,-1:33,180,-1:29,70:2,183,70:4,-1,70:4" +
",187,70:8,-1:11,70:8,187,70:10,-1,70,-1:15,94:2,168,191,94:3,-1,94:13,-1:11" +
",94:14,191,94:4,-1,94,-1:17,221,-1:14,221,-1:25,221,-1:24,70:2,40,70,79,70:" +
"2,-1,70:13,-1:11,70:6,79,70:12,-1,70,-1:15,94,194,171,94:4,-1,94:13,-1:11,9" +
"4:2,194,94:16,-1,94,-1:17,183,-1:9,183,-1:27,183,-1:27,70:2,223,190,70:3,-1" +
",70:13,-1:11,70:14,190,70:4,-1,70,-1:15,94:2,251,94:4,-1,94:8,258,94:4,-1:1" +
"1,94:16,258,94:2,-1,94,-1:17,40,-1,40,-1:33,40,-1:29,121,70,41,70:4,-1,70:1" +
"3,-1:11,70:4,121,70:14,-1,70,-1:15,94:2,76,94:4,-1,131,94:6,131,94:5,-1:11," +
"94:19,-1,94,-1:17,223:2,-1:42,223,-1:21,70:2,84,70,107,70:2,-1,70:13,-1:11," +
"70:6,107,70:12,-1,70,-1:15,94:2,38,94:4,-1,94:12,100,-1:11,94:17,100,94,-1," +
"94,-1:17,102,223,40,-1:33,40,-1:7,223,-1:21,70:2,44,70:4,-1,70:11,83,70,-1:" +
"11,70:12,83,70:6,-1,70,-1:15,94:2,78,94:4,-1,119,94:6,119,94:5,-1:11,94:19," +
"-1,94,-1:15,41,-1,80,223,-1:32,41,-1:9,223,-1:21,70:2,225,70:4,-1,70:9,193," +
"70:3,-1:11,70:11,193,70:7,-1,70,-1:15,94:2,183,94:4,-1,94:4,212,94:8,-1:11," +
"94:8,212,94:10,-1,94,-1:17,43,-1:10,43,-1:28,43,-1:25,70:2,43,70:4,-1,70:5," +
"123,70:7,-1:11,70:10,123,70:8,-1,70,-1:15,94:2,231,214,94:3,231,94:13,-1:11" +
",94:14,214,94:4,-1,94,-1:17,227,-1:2,227,-1:35,227,-1:26,70:2,81,70:2,104,7" +
"0,-1,70:13,-1:11,70:9,104,70:9,-1,70,-1:15,94:2,46,94,85,94:2,46,94:13,-1:1" +
"1,94:6,85,94:12,-1,94,-1:17,229,-1,229,-1:33,229,-1:29,70:2,227,70:2,199,70" +
",-1,70:13,-1:11,70:9,199,70:9,-1,70,-1:15,94:2,43,94:4,-1,94:5,105,94:7,-1:" +
"11,94:10,105,94:8,-1,94,-1:15,70:2,47,86,70:3,-1,70:13,-1:11,70:14,86,70:4," +
"-1,70,-1:15,94:2,40,94,137,94:2,-1,94:13,-1:11,94:6,137,94:12,-1,94,-1:15,4" +
"1,-1,41,-1:33,41,-1:31,70:2,233,70:4,-1,70:4,202,70:8,-1:11,70:8,202,70:10," +
"-1,70,-1:15,94:2,223,216,94:3,-1,94:13,-1:11,94:14,216,94:4,-1,94,-1:15,41," +
"-1,103,-1:14,225,-1:18,41,-1:6,225,-1:24,70:2,253,70:4,-1,70,205,70:11,-1:1" +
"1,70:13,205,70:5,-1,70,-1:15,133,94,41,94:4,-1,94:13,-1:11,94:4,133,94:14,-" +
"1,94,-1:17,45,223,84,-1:33,84,-1:7,223,-1:21,70:2,48,70,87,70:2,-1,70:13,-1" +
":11,70:6,87,70:12,-1,70,-1:15,94:2,84,94,124,94:2,-1,94:13,-1:11,94:6,124,9" +
"4:12,-1,94,-1:17,84,-1,84,-1:33,84,-1:29,70:2,89,70:4,-1,70:10,110,70:2,-1:" +
"11,70:5,110,70:13,-1,70,-1:15,94:2,229,94,220,94:2,-1,94:13,-1:11,94:6,220," +
"94:12,-1,94,-1:17,82,-1:2,227,-1:7,43,-1:27,227,43,-1:25,70:2,235,70:4,-1,7" +
"0:4,208,70:8,-1:11,70:8,208,70:10,-1,70,-1:15,94:2,44,94:4,-1,94:11,106,94," +
"-1:11,94:12,106,94:6,-1,94,-1:15,41,-1,84,-1,84,-1:31,41,-1,84,-1:29,70:2,2" +
"36,70:4,-1,211,70:6,211,70:5,-1:11,70:19,-1,70,-1:15,94:2,81,94:2,122,94,-1" +
",94:13,-1:11,94:9,122,94:9,-1,94,-1:15,70:2,51,90,70:3,-1,70:13,-1:11,70:14" +
",90,70:4,-1,70,-1:15,94:2,227,94:2,222,94,-1,94:13,-1:11,94:9,222,94:9,-1,9" +
"4,-1:17,231:2,-1:3,231,-1:38,231,-1:21,94:2,49,94,88,94:2,49,94:13,-1:11,94" +
":6,88,94:12,-1,94,-1:17,46,-1,46,-1:2,46,-1:5,43,-1:24,46,-1:3,43,-1:25,94:" +
"2,47,125,94:3,-1,94:13,-1:11,94:14,125,94:4,-1,94,-1:17,46,-1,46,-1:2,46,-1" +
":30,46,-1:29,94:2,233,94:4,-1,94:4,224,94:8,-1:11,94:8,224,94:10,-1,94,-1:1" +
"7,232,-1,229,-1:12,225,-1:20,229,-1:4,225,-1:24,94:2,253,94:4,-1,94,226,94:" +
"11,-1:11,94:13,226,94:5,-1,94,-1:17,81,-1:2,81,-1:35,81,-1:26,94:2,48,94,10" +
"9,94:2,-1,94:13,-1:11,94:6,109,94:12,-1,94,-1:15,94:2,89,94:4,-1,94:10,126," +
"94:2,-1:11,94:5,126,94:13,-1,94,-1:15,94:2,235,94:4,-1,94:4,228,94:8,-1:11," +
"94:8,228,94:10,-1,94,-1:15,94:2,236,94:4,-1,230,94:6,230,94:5,-1:11,94:19,-" +
"1,94,-1:15,94:2,51,111,94:3,-1,94:13,-1:11,94:14,111,94:4,-1,94,-1:17,49,-1" +
",49,-1:2,49,-1:30,49,-1:31,234,-1:6,253,-1:2,233,-1:27,233,-1:4,253,-1:24,8" +
"9,-1:15,89,-1:18,89,-1:32,51:2,-1:42,51,-1:7,1,52,91,112,53,52:4,-1,52:58,1" +
",52:3,55,52:4,-1,52:58,1,7:3,-1,7:4,-1,7:58,1,56:3,57,56,7,56:14,58,56:43,5" +
"9,56:2,-1:56,67,-1:25,70:2,255,245,70,244,70,-1,70:13,-1:11,70:9,244,70:4,2" +
"45,70:4,-1,70,-1:16,171,198,201,-1:30,171,-1:11,201,-1:21,70:2,201,172,70:3" +
",-1,70:13,-1:11,70:14,172,70:4,-1,70,-1:15,70,169,192,70:4,-1,70:13,-1:11,7" +
"0:2,169,70:16,-1,70,-1:15,94:2,156,94,173,94:2,-1,94:9,176,94:3,-1:11,94:6," +
"173,94:4,176,94:7,-1,94,-1:15,70:2,221,70:4,-1,70:9,184,70:3,-1:11,70:11,18" +
"4,70:7,-1,70,-1:15,94:2,210,94:4,-1,94:9,206,94:3,-1:11,94:11,206,94:7,-1,9" +
"4,-1:15,70:2,229,70,196,70:2,-1,70:13,-1:11,70:6,196,70:12,-1,70,-1:15,94:2" +
",201,200,94:3,-1,94:13,-1:11,94:14,200,94:4,-1,94,-1:17,225,-1:14,225,-1:25" +
",225,-1:24,94,197,192,94:4,-1,94:13,-1:11,94:2,197,94:16,-1,94,-1:17,235,-1" +
":9,235,-1:27,235,-1:27,70:2,144,70,142,70:2,-1,70:9,145,70:3,-1:11,70:6,142" +
",70:4,145,70:7,-1,70,-1:16,192,207,201,-1:30,192,-1:11,201,-1:21,94:2,221,9" +
"4:4,-1,94:9,209,94:3,-1:11,94:11,209,94:7,-1,94,-1:15,70:2,156,70,151,70:2," +
"-1,70:9,154,70:3,-1:11,70:6,151,70:4,154,70:7,-1,70,-1:15,94:2,225,94:4,-1," +
"94:9,218,94:3,-1:11,94:11,218,94:7,-1,94,-1:15,70:2,159,70:4,-1,70:6,157,70" +
":6,-1:11,70:7,157,70:11,-1,70,-1:15,70:2,162,70:4,-1,70:9,247,70:3,-1:11,70" +
":11,247,70:7,-1,70,-1:15,70:2,165,70:4,-1,70:6,160,70:6,-1:11,70:7,160,70:1" +
"1,-1,70,-1");

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
	   	case STR:
	   		yybegin(ESTOFERROR);
	   		return new Symbol(TokenConstants.ERROR, "Se alcanzo EOF en String");
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
						{return new Symbol(TokenConstants.MINUS); }
					case -3:
						break;
					case 3:
						{return new Symbol(TokenConstants.LPAREN); }
					case -4:
						break;
					case 4:
						{return new Symbol(TokenConstants.MULT); }
					case -5:
						break;
					case 5:
						{curr_lineno++;}
					case -6:
						break;
					case 6:
						{return new Symbol(TokenConstants.RPAREN); }
					case -7:
						break;
					case 7:
						{ /* This rule should be the very last
                                     in your lexical specification and
                                     will match match everything not
                                     matched by other lexical rules. */
                                  System.err.println("LEXER BUG - UNMATCHED: " + yytext()); }
					case -8:
						break;
					case 8:
						{}
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
						{return new Symbol(TokenConstants.ERROR, "_");}
					case -25:
						break;
					case 25:
						{	AbstractSymbol inte = AbstractTable.inttable.addString(yytext());
							return new Symbol(TokenConstants.INT_CONST,inte); }
					case -26:
						break;
					case 26:
						{yybegin(STR); string_buf.setLength(0); }
					case -27:
						break;
					case 27:
						{yybegin(COMMENTLINEAL);}
					case -28:
						break;
					case 28:
						{yybegin(COMMENT);comentario++;}
					case -29:
						break;
					case 29:
						{return new Symbol(TokenConstants.ERROR, "Unmatched *)");}
					case -30:
						break;
					case 30:
						{return new Symbol(TokenConstants.DARROW); }
					case -31:
						break;
					case 31:
						{return new Symbol(TokenConstants.FI);}
					case -32:
						break;
					case 32:
						{return new Symbol(TokenConstants.IF);}
					case -33:
						break;
					case 33:
						{return new Symbol(TokenConstants.IN);}
					case -34:
						break;
					case 34:
						{return new Symbol(TokenConstants.OF);}
					case -35:
						break;
					case 35:
						{return new Symbol(TokenConstants.ASSIGN); }
					case -36:
						break;
					case 36:
						{return new Symbol(TokenConstants.LE); }
					case -37:
						break;
					case 37:
						{return new Symbol(TokenConstants.LET);}
					case -38:
						break;
					case 38:
						{return new Symbol(TokenConstants.NEW);}
					case -39:
						break;
					case 39:
						{return new Symbol(TokenConstants.NOT); }
					case -40:
						break;
					case 40:
						{return new Symbol(TokenConstants.CASE);}
					case -41:
						break;
					case 41:
						{return new Symbol(TokenConstants.ESAC);}
					case -42:
						break;
					case 42:
						{return new Symbol(TokenConstants.POOL);}
					case -43:
						break;
					case 43:
						{return new Symbol(TokenConstants.THEN);}
					case -44:
						break;
					case 44:
						{return new Symbol(TokenConstants.LOOP);}
					case -45:
						break;
					case 45:
						{return new Symbol(TokenConstants.ELSE);}
					case -46:
						break;
					case 46:
						{return new Symbol(TokenConstants.BOOL_CONST, "true"); }
					case -47:
						break;
					case 47:
						{return new Symbol(TokenConstants.CLASS);}
					case -48:
						break;
					case 48:
						{return new Symbol(TokenConstants.WHILE);}
					case -49:
						break;
					case 49:
						{return new Symbol(TokenConstants.BOOL_CONST, "false"); }
					case -50:
						break;
					case 50:
						{return new Symbol(TokenConstants.ISVOID);}
					case -51:
						break;
					case 51:
						{return new Symbol(TokenConstants.INHERITS);}
					case -52:
						break;
					case 52:
						{}
					case -53:
						break;
					case 53:
						{curr_lineno++;}
					case -54:
						break;
					case 54:
						{if (--comentario == 0) {yybegin(YYINITIAL);}}
					case -55:
						break;
					case 55:
						{curr_lineno++; yybegin(YYINITIAL);}
					case -56:
						break;
					case 56:
						{string_buf.append(yytext());}
					case -57:
						break;
					case 57:
						{curr_lineno--; yybegin(YYINITIAL); string_buf.setLength(0); return new Symbol(TokenConstants.ERROR, "Undetermined string constant");}
					case -58:
						break;
					case 58:
						{return new Symbol(TokenConstants.ERROR);}
					case -59:
						break;
					case 59:
						{yybegin(YYINITIAL); return new Symbol(TokenConstants.STR_CONST, AbstractTable.stringtable.addString(string_buf.toString()));}
					case -60:
						break;
					case 60:
						{string_buf.append("\n");}
					case -61:
						break;
					case 61:
						{string_buf.append("\\");}
					case -62:
						break;
					case 62:
						{string_buf.append("\f");}
					case -63:
						break;
					case 63:
						{string_buf.append("\t");}
					case -64:
						break;
					case 64:
						{string_buf.append("\n");curr_lineno++; }
					case -65:
						break;
					case 65:
						{string_buf.append("\"");}
					case -66:
						break;
					case 66:
						{string_buf.append("\b");}
					case -67:
						break;
					case 67:
						{string_buf.append("\\n");}
					case -68:
						break;
					case 69:
						{ /* This rule should be the very last
                                     in your lexical specification and
                                     will match match everything not
                                     matched by other lexical rules. */
                                  System.err.println("LEXER BUG - UNMATCHED: " + yytext()); }
					case -69:
						break;
					case 70:
						{	AbstractSymbol tip = AbstractTable.idtable.addString(yytext());
							return new Symbol(TokenConstants.TYPEID,tip); }
					case -70:
						break;
					case 71:
						{	AbstractSymbol obj = AbstractTable.idtable.addString(yytext());
							return new Symbol(TokenConstants.OBJECTID,obj); }
					case -71:
						break;
					case 72:
						{return new Symbol(TokenConstants.FI);}
					case -72:
						break;
					case 73:
						{return new Symbol(TokenConstants.IF);}
					case -73:
						break;
					case 74:
						{return new Symbol(TokenConstants.IN);}
					case -74:
						break;
					case 75:
						{return new Symbol(TokenConstants.OF);}
					case -75:
						break;
					case 76:
						{return new Symbol(TokenConstants.LET);}
					case -76:
						break;
					case 77:
						{return new Symbol(TokenConstants.NEW);}
					case -77:
						break;
					case 78:
						{return new Symbol(TokenConstants.NOT); }
					case -78:
						break;
					case 79:
						{return new Symbol(TokenConstants.CASE);}
					case -79:
						break;
					case 80:
						{return new Symbol(TokenConstants.ESAC);}
					case -80:
						break;
					case 81:
						{return new Symbol(TokenConstants.POOL);}
					case -81:
						break;
					case 82:
						{return new Symbol(TokenConstants.THEN);}
					case -82:
						break;
					case 83:
						{return new Symbol(TokenConstants.LOOP);}
					case -83:
						break;
					case 84:
						{return new Symbol(TokenConstants.ELSE);}
					case -84:
						break;
					case 85:
						{return new Symbol(TokenConstants.BOOL_CONST, "true"); }
					case -85:
						break;
					case 86:
						{return new Symbol(TokenConstants.CLASS);}
					case -86:
						break;
					case 87:
						{return new Symbol(TokenConstants.WHILE);}
					case -87:
						break;
					case 88:
						{return new Symbol(TokenConstants.BOOL_CONST, "false"); }
					case -88:
						break;
					case 89:
						{return new Symbol(TokenConstants.ISVOID);}
					case -89:
						break;
					case 90:
						{return new Symbol(TokenConstants.INHERITS);}
					case -90:
						break;
					case 91:
						{}
					case -91:
						break;
					case 93:
						{	AbstractSymbol tip = AbstractTable.idtable.addString(yytext());
							return new Symbol(TokenConstants.TYPEID,tip); }
					case -92:
						break;
					case 94:
						{	AbstractSymbol obj = AbstractTable.idtable.addString(yytext());
							return new Symbol(TokenConstants.OBJECTID,obj); }
					case -93:
						break;
					case 95:
						{return new Symbol(TokenConstants.FI);}
					case -94:
						break;
					case 96:
						{return new Symbol(TokenConstants.IF);}
					case -95:
						break;
					case 97:
						{return new Symbol(TokenConstants.IN);}
					case -96:
						break;
					case 98:
						{return new Symbol(TokenConstants.OF);}
					case -97:
						break;
					case 99:
						{return new Symbol(TokenConstants.LET);}
					case -98:
						break;
					case 100:
						{return new Symbol(TokenConstants.NEW);}
					case -99:
						break;
					case 101:
						{return new Symbol(TokenConstants.NOT); }
					case -100:
						break;
					case 102:
						{return new Symbol(TokenConstants.CASE);}
					case -101:
						break;
					case 103:
						{return new Symbol(TokenConstants.ESAC);}
					case -102:
						break;
					case 104:
						{return new Symbol(TokenConstants.POOL);}
					case -103:
						break;
					case 105:
						{return new Symbol(TokenConstants.THEN);}
					case -104:
						break;
					case 106:
						{return new Symbol(TokenConstants.LOOP);}
					case -105:
						break;
					case 107:
						{return new Symbol(TokenConstants.ELSE);}
					case -106:
						break;
					case 108:
						{return new Symbol(TokenConstants.CLASS);}
					case -107:
						break;
					case 109:
						{return new Symbol(TokenConstants.WHILE);}
					case -108:
						break;
					case 110:
						{return new Symbol(TokenConstants.ISVOID);}
					case -109:
						break;
					case 111:
						{return new Symbol(TokenConstants.INHERITS);}
					case -110:
						break;
					case 112:
						{}
					case -111:
						break;
					case 114:
						{	AbstractSymbol tip = AbstractTable.idtable.addString(yytext());
							return new Symbol(TokenConstants.TYPEID,tip); }
					case -112:
						break;
					case 115:
						{	AbstractSymbol obj = AbstractTable.idtable.addString(yytext());
							return new Symbol(TokenConstants.OBJECTID,obj); }
					case -113:
						break;
					case 116:
						{return new Symbol(TokenConstants.FI);}
					case -114:
						break;
					case 117:
						{return new Symbol(TokenConstants.IF);}
					case -115:
						break;
					case 118:
						{return new Symbol(TokenConstants.LET);}
					case -116:
						break;
					case 119:
						{return new Symbol(TokenConstants.NOT); }
					case -117:
						break;
					case 120:
						{return new Symbol(TokenConstants.CASE);}
					case -118:
						break;
					case 121:
						{return new Symbol(TokenConstants.ESAC);}
					case -119:
						break;
					case 122:
						{return new Symbol(TokenConstants.POOL);}
					case -120:
						break;
					case 123:
						{return new Symbol(TokenConstants.THEN);}
					case -121:
						break;
					case 124:
						{return new Symbol(TokenConstants.ELSE);}
					case -122:
						break;
					case 125:
						{return new Symbol(TokenConstants.CLASS);}
					case -123:
						break;
					case 126:
						{return new Symbol(TokenConstants.ISVOID);}
					case -124:
						break;
					case 128:
						{	AbstractSymbol tip = AbstractTable.idtable.addString(yytext());
							return new Symbol(TokenConstants.TYPEID,tip); }
					case -125:
						break;
					case 129:
						{	AbstractSymbol obj = AbstractTable.idtable.addString(yytext());
							return new Symbol(TokenConstants.OBJECTID,obj); }
					case -126:
						break;
					case 130:
						{return new Symbol(TokenConstants.FI);}
					case -127:
						break;
					case 131:
						{return new Symbol(TokenConstants.LET);}
					case -128:
						break;
					case 132:
						{return new Symbol(TokenConstants.CASE);}
					case -129:
						break;
					case 133:
						{return new Symbol(TokenConstants.ESAC);}
					case -130:
						break;
					case 135:
						{	AbstractSymbol tip = AbstractTable.idtable.addString(yytext());
							return new Symbol(TokenConstants.TYPEID,tip); }
					case -131:
						break;
					case 136:
						{	AbstractSymbol obj = AbstractTable.idtable.addString(yytext());
							return new Symbol(TokenConstants.OBJECTID,obj); }
					case -132:
						break;
					case 137:
						{return new Symbol(TokenConstants.CASE);}
					case -133:
						break;
					case 139:
						{	AbstractSymbol tip = AbstractTable.idtable.addString(yytext());
							return new Symbol(TokenConstants.TYPEID,tip); }
					case -134:
						break;
					case 140:
						{	AbstractSymbol obj = AbstractTable.idtable.addString(yytext());
							return new Symbol(TokenConstants.OBJECTID,obj); }
					case -135:
						break;
					case 142:
						{	AbstractSymbol tip = AbstractTable.idtable.addString(yytext());
							return new Symbol(TokenConstants.TYPEID,tip); }
					case -136:
						break;
					case 143:
						{	AbstractSymbol obj = AbstractTable.idtable.addString(yytext());
							return new Symbol(TokenConstants.OBJECTID,obj); }
					case -137:
						break;
					case 145:
						{	AbstractSymbol tip = AbstractTable.idtable.addString(yytext());
							return new Symbol(TokenConstants.TYPEID,tip); }
					case -138:
						break;
					case 146:
						{	AbstractSymbol obj = AbstractTable.idtable.addString(yytext());
							return new Symbol(TokenConstants.OBJECTID,obj); }
					case -139:
						break;
					case 148:
						{	AbstractSymbol tip = AbstractTable.idtable.addString(yytext());
							return new Symbol(TokenConstants.TYPEID,tip); }
					case -140:
						break;
					case 149:
						{	AbstractSymbol obj = AbstractTable.idtable.addString(yytext());
							return new Symbol(TokenConstants.OBJECTID,obj); }
					case -141:
						break;
					case 151:
						{	AbstractSymbol tip = AbstractTable.idtable.addString(yytext());
							return new Symbol(TokenConstants.TYPEID,tip); }
					case -142:
						break;
					case 152:
						{	AbstractSymbol obj = AbstractTable.idtable.addString(yytext());
							return new Symbol(TokenConstants.OBJECTID,obj); }
					case -143:
						break;
					case 154:
						{	AbstractSymbol tip = AbstractTable.idtable.addString(yytext());
							return new Symbol(TokenConstants.TYPEID,tip); }
					case -144:
						break;
					case 155:
						{	AbstractSymbol obj = AbstractTable.idtable.addString(yytext());
							return new Symbol(TokenConstants.OBJECTID,obj); }
					case -145:
						break;
					case 157:
						{	AbstractSymbol tip = AbstractTable.idtable.addString(yytext());
							return new Symbol(TokenConstants.TYPEID,tip); }
					case -146:
						break;
					case 158:
						{	AbstractSymbol obj = AbstractTable.idtable.addString(yytext());
							return new Symbol(TokenConstants.OBJECTID,obj); }
					case -147:
						break;
					case 160:
						{	AbstractSymbol tip = AbstractTable.idtable.addString(yytext());
							return new Symbol(TokenConstants.TYPEID,tip); }
					case -148:
						break;
					case 161:
						{	AbstractSymbol obj = AbstractTable.idtable.addString(yytext());
							return new Symbol(TokenConstants.OBJECTID,obj); }
					case -149:
						break;
					case 163:
						{	AbstractSymbol tip = AbstractTable.idtable.addString(yytext());
							return new Symbol(TokenConstants.TYPEID,tip); }
					case -150:
						break;
					case 164:
						{	AbstractSymbol obj = AbstractTable.idtable.addString(yytext());
							return new Symbol(TokenConstants.OBJECTID,obj); }
					case -151:
						break;
					case 166:
						{	AbstractSymbol tip = AbstractTable.idtable.addString(yytext());
							return new Symbol(TokenConstants.TYPEID,tip); }
					case -152:
						break;
					case 167:
						{	AbstractSymbol obj = AbstractTable.idtable.addString(yytext());
							return new Symbol(TokenConstants.OBJECTID,obj); }
					case -153:
						break;
					case 169:
						{	AbstractSymbol tip = AbstractTable.idtable.addString(yytext());
							return new Symbol(TokenConstants.TYPEID,tip); }
					case -154:
						break;
					case 170:
						{	AbstractSymbol obj = AbstractTable.idtable.addString(yytext());
							return new Symbol(TokenConstants.OBJECTID,obj); }
					case -155:
						break;
					case 172:
						{	AbstractSymbol tip = AbstractTable.idtable.addString(yytext());
							return new Symbol(TokenConstants.TYPEID,tip); }
					case -156:
						break;
					case 173:
						{	AbstractSymbol obj = AbstractTable.idtable.addString(yytext());
							return new Symbol(TokenConstants.OBJECTID,obj); }
					case -157:
						break;
					case 175:
						{	AbstractSymbol tip = AbstractTable.idtable.addString(yytext());
							return new Symbol(TokenConstants.TYPEID,tip); }
					case -158:
						break;
					case 176:
						{	AbstractSymbol obj = AbstractTable.idtable.addString(yytext());
							return new Symbol(TokenConstants.OBJECTID,obj); }
					case -159:
						break;
					case 178:
						{	AbstractSymbol tip = AbstractTable.idtable.addString(yytext());
							return new Symbol(TokenConstants.TYPEID,tip); }
					case -160:
						break;
					case 179:
						{	AbstractSymbol obj = AbstractTable.idtable.addString(yytext());
							return new Symbol(TokenConstants.OBJECTID,obj); }
					case -161:
						break;
					case 181:
						{	AbstractSymbol tip = AbstractTable.idtable.addString(yytext());
							return new Symbol(TokenConstants.TYPEID,tip); }
					case -162:
						break;
					case 182:
						{	AbstractSymbol obj = AbstractTable.idtable.addString(yytext());
							return new Symbol(TokenConstants.OBJECTID,obj); }
					case -163:
						break;
					case 184:
						{	AbstractSymbol tip = AbstractTable.idtable.addString(yytext());
							return new Symbol(TokenConstants.TYPEID,tip); }
					case -164:
						break;
					case 185:
						{	AbstractSymbol obj = AbstractTable.idtable.addString(yytext());
							return new Symbol(TokenConstants.OBJECTID,obj); }
					case -165:
						break;
					case 187:
						{	AbstractSymbol tip = AbstractTable.idtable.addString(yytext());
							return new Symbol(TokenConstants.TYPEID,tip); }
					case -166:
						break;
					case 188:
						{	AbstractSymbol obj = AbstractTable.idtable.addString(yytext());
							return new Symbol(TokenConstants.OBJECTID,obj); }
					case -167:
						break;
					case 190:
						{	AbstractSymbol tip = AbstractTable.idtable.addString(yytext());
							return new Symbol(TokenConstants.TYPEID,tip); }
					case -168:
						break;
					case 191:
						{	AbstractSymbol obj = AbstractTable.idtable.addString(yytext());
							return new Symbol(TokenConstants.OBJECTID,obj); }
					case -169:
						break;
					case 193:
						{	AbstractSymbol tip = AbstractTable.idtable.addString(yytext());
							return new Symbol(TokenConstants.TYPEID,tip); }
					case -170:
						break;
					case 194:
						{	AbstractSymbol obj = AbstractTable.idtable.addString(yytext());
							return new Symbol(TokenConstants.OBJECTID,obj); }
					case -171:
						break;
					case 196:
						{	AbstractSymbol tip = AbstractTable.idtable.addString(yytext());
							return new Symbol(TokenConstants.TYPEID,tip); }
					case -172:
						break;
					case 197:
						{	AbstractSymbol obj = AbstractTable.idtable.addString(yytext());
							return new Symbol(TokenConstants.OBJECTID,obj); }
					case -173:
						break;
					case 199:
						{	AbstractSymbol tip = AbstractTable.idtable.addString(yytext());
							return new Symbol(TokenConstants.TYPEID,tip); }
					case -174:
						break;
					case 200:
						{	AbstractSymbol obj = AbstractTable.idtable.addString(yytext());
							return new Symbol(TokenConstants.OBJECTID,obj); }
					case -175:
						break;
					case 202:
						{	AbstractSymbol tip = AbstractTable.idtable.addString(yytext());
							return new Symbol(TokenConstants.TYPEID,tip); }
					case -176:
						break;
					case 203:
						{	AbstractSymbol obj = AbstractTable.idtable.addString(yytext());
							return new Symbol(TokenConstants.OBJECTID,obj); }
					case -177:
						break;
					case 205:
						{	AbstractSymbol tip = AbstractTable.idtable.addString(yytext());
							return new Symbol(TokenConstants.TYPEID,tip); }
					case -178:
						break;
					case 206:
						{	AbstractSymbol obj = AbstractTable.idtable.addString(yytext());
							return new Symbol(TokenConstants.OBJECTID,obj); }
					case -179:
						break;
					case 208:
						{	AbstractSymbol tip = AbstractTable.idtable.addString(yytext());
							return new Symbol(TokenConstants.TYPEID,tip); }
					case -180:
						break;
					case 209:
						{	AbstractSymbol obj = AbstractTable.idtable.addString(yytext());
							return new Symbol(TokenConstants.OBJECTID,obj); }
					case -181:
						break;
					case 211:
						{	AbstractSymbol tip = AbstractTable.idtable.addString(yytext());
							return new Symbol(TokenConstants.TYPEID,tip); }
					case -182:
						break;
					case 212:
						{	AbstractSymbol obj = AbstractTable.idtable.addString(yytext());
							return new Symbol(TokenConstants.OBJECTID,obj); }
					case -183:
						break;
					case 214:
						{	AbstractSymbol obj = AbstractTable.idtable.addString(yytext());
							return new Symbol(TokenConstants.OBJECTID,obj); }
					case -184:
						break;
					case 216:
						{	AbstractSymbol obj = AbstractTable.idtable.addString(yytext());
							return new Symbol(TokenConstants.OBJECTID,obj); }
					case -185:
						break;
					case 218:
						{	AbstractSymbol obj = AbstractTable.idtable.addString(yytext());
							return new Symbol(TokenConstants.OBJECTID,obj); }
					case -186:
						break;
					case 220:
						{	AbstractSymbol obj = AbstractTable.idtable.addString(yytext());
							return new Symbol(TokenConstants.OBJECTID,obj); }
					case -187:
						break;
					case 222:
						{	AbstractSymbol obj = AbstractTable.idtable.addString(yytext());
							return new Symbol(TokenConstants.OBJECTID,obj); }
					case -188:
						break;
					case 224:
						{	AbstractSymbol obj = AbstractTable.idtable.addString(yytext());
							return new Symbol(TokenConstants.OBJECTID,obj); }
					case -189:
						break;
					case 226:
						{	AbstractSymbol obj = AbstractTable.idtable.addString(yytext());
							return new Symbol(TokenConstants.OBJECTID,obj); }
					case -190:
						break;
					case 228:
						{	AbstractSymbol obj = AbstractTable.idtable.addString(yytext());
							return new Symbol(TokenConstants.OBJECTID,obj); }
					case -191:
						break;
					case 230:
						{	AbstractSymbol obj = AbstractTable.idtable.addString(yytext());
							return new Symbol(TokenConstants.OBJECTID,obj); }
					case -192:
						break;
					case 242:
						{	AbstractSymbol tip = AbstractTable.idtable.addString(yytext());
							return new Symbol(TokenConstants.TYPEID,tip); }
					case -193:
						break;
					case 244:
						{	AbstractSymbol tip = AbstractTable.idtable.addString(yytext());
							return new Symbol(TokenConstants.TYPEID,tip); }
					case -194:
						break;
					case 245:
						{	AbstractSymbol tip = AbstractTable.idtable.addString(yytext());
							return new Symbol(TokenConstants.TYPEID,tip); }
					case -195:
						break;
					case 246:
						{	AbstractSymbol obj = AbstractTable.idtable.addString(yytext());
							return new Symbol(TokenConstants.OBJECTID,obj); }
					case -196:
						break;
					case 247:
						{	AbstractSymbol tip = AbstractTable.idtable.addString(yytext());
							return new Symbol(TokenConstants.TYPEID,tip); }
					case -197:
						break;
					case 248:
						{	AbstractSymbol obj = AbstractTable.idtable.addString(yytext());
							return new Symbol(TokenConstants.OBJECTID,obj); }
					case -198:
						break;
					case 249:
						{	AbstractSymbol tip = AbstractTable.idtable.addString(yytext());
							return new Symbol(TokenConstants.TYPEID,tip); }
					case -199:
						break;
					case 250:
						{	AbstractSymbol obj = AbstractTable.idtable.addString(yytext());
							return new Symbol(TokenConstants.OBJECTID,obj); }
					case -200:
						break;
					case 252:
						{	AbstractSymbol obj = AbstractTable.idtable.addString(yytext());
							return new Symbol(TokenConstants.OBJECTID,obj); }
					case -201:
						break;
					case 254:
						{	AbstractSymbol tip = AbstractTable.idtable.addString(yytext());
							return new Symbol(TokenConstants.TYPEID,tip); }
					case -202:
						break;
					case 256:
						{	AbstractSymbol obj = AbstractTable.idtable.addString(yytext());
							return new Symbol(TokenConstants.OBJECTID,obj); }
					case -203:
						break;
					case 257:
						{	AbstractSymbol tip = AbstractTable.idtable.addString(yytext());
							return new Symbol(TokenConstants.TYPEID,tip); }
					case -204:
						break;
					case 258:
						{	AbstractSymbol obj = AbstractTable.idtable.addString(yytext());
							return new Symbol(TokenConstants.OBJECTID,obj); }
					case -205:
						break;
					case 259:
						{	AbstractSymbol tip = AbstractTable.idtable.addString(yytext());
							return new Symbol(TokenConstants.TYPEID,tip); }
					case -206:
						break;
					case 260:
						{	AbstractSymbol tip = AbstractTable.idtable.addString(yytext());
							return new Symbol(TokenConstants.TYPEID,tip); }
					case -207:
						break;
					case 261:
						{	AbstractSymbol tip = AbstractTable.idtable.addString(yytext());
							return new Symbol(TokenConstants.TYPEID,tip); }
					case -208:
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
