package alumno

import com.itextpdf.text.BadElementException;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Font.FontFamily;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPCellEvent;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.draw.DottedLineSeparator
import java.util.logging.Logger
import java.util.logging.Level;

class CreateDirectoryService {

    public static final float[][] COLUMNS = [[36, 36, 185, 562] , [ 209, 36, 358, 562 ]]

    def createPDF() {
        // Now setup PDF output document
        Rectangle pagesize = new Rectangle(394f, 612f);
        Document document = new Document(pagesize, 36f, 36f, 18f, 18f);
        PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream("wildcats2012.pdf"));
        writer.setBoxSize("page", pagesize);
        writer.setPageEvent( { Object[] args ->
            PdfContentByte canvas = writer.getDirectContent();
            canvas.beginText();
            Font times = new Font(FontFamily.TIMES_ROMAN, 10);
            BaseFont bf_times = times.getCalculatedBaseFont(false);
            canvas.setFontAndSize(bf_times, 10);
            switch(writer.getPageNumber() % 2) {
                case 0:
                    canvas.showTextAligned(Element.ALIGN_LEFT, String.format("%d",writer.getPageNumber()), 40, 36, 0);
                    break;
                case 1:
                    canvas.showTextAligned(Element.ALIGN_RIGHT, String.format("%d",writer.getPageNumber()), 330, 36, 0);
                    break;
            }
            canvas.endText()
            } as PdfPageEventHelper
        )

        document.open();

        writer.setCompressionLevel(0);

        writeStudentRecords(writer, document);
 //       writeDifferentNamedParentsResults(writer,document);

        document.close();
    }

    public void writeStudentRecords(PdfWriter writer, Document document) throws DocumentException, BadElementException, MalformedURLException, IOException {

        ColumnText currentColumn = new ColumnText(writer.getDirectContent());
        int column = 0;
        currentColumn.setSimpleColumn(
                COLUMNS[column][0], COLUMNS[column][1],
                COLUMNS[column][2], COLUMNS[column][3]);
        currentColumn.setLeading(9f);
        int status = ColumnText.START_COLUMN;
        Paragraph studentInfo;
        float yLocation;

        def students = Student.list()
        students.each() {
            yLocation = currentColumn.getYLine();
            studentInfo = buildStudentInfo(writer, it);
            if (!studentInfo.isEmpty()) {
                currentColumn.addText(studentInfo);
                status = currentColumn.go(true);
                if (ColumnText.hasMoreText(status)) {
                    column = Math.abs(column - 1);
                    if (column == 0)
                        document.newPage();
                    currentColumn.setSimpleColumn(
                            COLUMNS[column][0], COLUMNS[column][1],
                            COLUMNS[column][2], COLUMNS[column][3]);
                    yLocation = COLUMNS[column][3];
                }
                currentColumn.setYLine(yLocation);
                currentColumn.setText(studentInfo);
                status = currentColumn.go(false);
            }
        }
    }

    public Paragraph buildStudentInfo(PdfWriter writer, Student currentStudent) throws BadElementException, MalformedURLException, IOException, DocumentException {

        Paragraph para = new Paragraph();
        try {
            para.setFont(FontFactory.getFont(FontFactory.TIMES_ROMAN, FontFactory.defaultEncoding, 9));

            String finalData = null;
            Chunk studentName = null;

            // Build initial row for student name and home number
            Font font = new Font(FontFamily.TIMES_ROMAN, 9, Font.BOLD);
            def currentStudentName = currentStudent.getLastName() + ", " + currentStudent.getFirstName()
            studentName = new Chunk(currentStudentName, font)
            para.add(studentName)
            para.add(new Chunk(new DottedLineSeparator()));

            def houseHoldsList = currentStudent.getRegistration()?.getHouseholds()
            houseHoldsList.eachWithIndex { currentHousehold, i ->
                if (i==0) {
                    // Home number
                    def homeNumberString = currentHousehold.getPhoneNumber()
                    if (homeNumberString) {
                        para.add(homeNumberString )
                    }
                    para.add("\r")

                    // Student grade and cell phone number
                    def studentInfo = new StringBuilder(currentStudent.getGrade().toString());
                    studentInfo.append("th grade");

                    def studentCellNum = currentStudent.getPhoneNumber()
                    if (studentCellNum) {
                        studentInfo.append(", " + studentCellNum);
                    }
                    studentInfo.append("\r");

                    // Student email
                    def studentEmail = currentStudent.getEmail()
                    if (studentEmail) {
                        studentInfo.append(studentEmail);
                        studentInfo.append("\r");
                    }

                    para.add(studentInfo.toString());

                    // Safehouse indicator
                    if (currentHousehold.getSafehome()) {
                        Phrase houseGlyph = new Phrase("J ", FontFactory.getFont(FontFactory.ZAPFDINGBATS, FontFactory.defaultEncoding, true, 10));
                        para.add(houseGlyph);
                    }
                }

                // Get parents from current household
                def householdParents = currentHousehold.getParents()
                householdParents.eachWithIndex { currentParent, j ->
                    def parentFullName = currentParent.getFirstName() + " " + currentParent.getLastName()
                    if (j)
                        para.add(" & ")
                    para.add(parentFullName)
                }
                para.add("\r")

                // Current household address line
                String householdAddress = buildHouseholdAddress(currentHousehold.getAddress(), currentHousehold.getCity(), currentHousehold.getZip())
                if (householdAddress)
                    para.add(householdAddress + "\r");

                // Get parents phone and email from current household
                householdParents.each{ currentParent->
                    def parentPhone = currentParent.getPhoneNumber()
                    if (parentPhone)
                        para.add(parentPhone)
                    def parentEmail = currentParent.getEmail()
                    if (parentEmail)
                        if (parentPhone) {
                            para.add(", ")
                        }
                        para.add(parentEmail)
                    if (parentEmail || parentPhone)
                        para.add("\r")
                }
            }
            // Space after student entry
            para.add("\r\r")
        }
        catch (Exception ex) {
            System.out.println("Exception in Student " + (currentStudent.toString()))
        }
        return para
    }

    public String buildHouseholdAddress(String parentAddress, String parentCity, String parentZip) {
        StringBuilder parentAddressBuffer
        if (!parentAddress.isEmpty()) {
            parentAddressBuffer = new StringBuilder(parentAddress);
            if (!parentCity.isEmpty()) {
                parentAddressBuffer.append(", " + parentCity);
            }
            parentAddressBuffer.append(", ");
            parentAddressBuffer.append(parentZip);

        }
        else
            parentAddressBuffer = new StringBuilder("");    // no info

        return parentAddressBuffer.toString();
    }


        /*   class CustomCell implements PdfPCellEvent {
                public void cellLayout(PdfPCell cell, Rectangle rect,
                                       PdfContentByte[] canvas) {
                    PdfContentByte cb = canvas[PdfPTable.LINECANVAS];
                    float[] a = [1.0f, 1.0f]
                    cb.setLineDash(a, 0);
                    cb.setColorStroke(BaseColor.GRAY);
                    cb.stroke();
                }
            }
        */
}
