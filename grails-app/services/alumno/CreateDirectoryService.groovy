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
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.draw.DottedLineSeparator
import com.itextpdf.text.Image
import org.hibernate.criterion.Order
import com.itextpdf.text.pdf.PdfCopy
import com.itextpdf.text.pdf.PdfReader


class CreateDirectoryService {

    public static final float[][] COLUMNS = [[36, 36, 185, 562] , [ 209, 36, 358, 562 ]]

    def MAX_NUM_OF_PARENT_ROWS = 34

    def concatenatePDF() {
        Document document = new Document();
        PdfCopy copy = new PdfCopy(document, new FileOutputStream("CompleteWildcats2012.pdf"));
        document.open();
        PdfReader reader;
        int n;
        reader = new PdfReader("TOCWildcats2012.pdf");
        n = reader.getNumberOfPages();
        for (int page = 0; page < n; ) {
            copy.addPage(copy.getImportedPage(reader, ++page));
        }
        copy.freeReader(reader);

        reader = new PdfReader("wildcats2012.pdf");
        n = reader.getNumberOfPages();
        for (int page = 0; page < n; ) {
            copy.addPage(copy.getImportedPage(reader, ++page));
        }
        copy.freeReader(reader);

        // step 5
        document.close();
    }


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
        writeDifferentNamedParentsResults(writer,document);
        if (writer.getPageNumber() % 2) {
            document.newPage(); //add another page when the last page is odd number
        }
        writer.setPageEvent(null);
        document.newPage(); // back inside cover
        writer.setPageEmpty(false)
        document.newPage(); // back cover
        Image img = Image.getInstance("web-app/images/BackCoverLGHS.png");
        img.setAbsolutePosition(100, 300); // just hardcoded location where it looks good
        PdfContentByte canvas = writer.getDirectContent();
        canvas.addImage(img);

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

        Student
        def c = Student.createCriteria()
        def students = c.list {
            and{
                order(new Order('lastName',true).ignoreCase())
                //order('lastName','asc')
                order('firstName','asc')
            }
        }
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
                // Ugly code to support ability to create Bob & Mary Smith from two parents instead of Bob Smith & Mary Smith
                // original code is in else clause when last names differ
                if (householdParents[0].getLastName() == householdParents[1]?.getLastName()) {
                    para.add(householdParents[0].getFirstName() + " & " + householdParents[1]?.getFirstName() + " " + householdParents[0].getLastName())
                }
                else {
                    householdParents.eachWithIndex { currentParent, j ->
                        def parentFullName = currentParent.getFirstName() + " " + currentParent.getLastName()
                        if (j)
                            para.add(" & ")
                        para.add(parentFullName)
                    }
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
        if (parentAddress) {
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


    public void writeDifferentNamedParentsResults(PdfWriter writer, Document document) throws DocumentException {

        PdfPTable table;
        def parentsWithDifferentNames =  buildParentsWithDifferentLastNames()
        parentsWithDifferentNames.eachWithIndex(){ differentParent, index ->
            if (index%MAX_NUM_OF_PARENT_ROWS == 0) {
                if (index > 0)
                    document.add(table)
                table = createNewPageForDifferentNamedParent(document)
            }
            def cell = new PdfPCell(new Phrase((differentParent[0].toString()),FontFactory.getFont(FontFactory.TIMES_ROMAN, 9)));
            cell.setBorderColor(BaseColor.GRAY)
            table.addCell(cell);
            cell = new PdfPCell(new Phrase((differentParent[1].toString()),FontFactory.getFont(FontFactory.TIMES_ROMAN, 9)));
            cell.setBorderColor(BaseColor.GRAY)
            table.addCell(cell);
            cell = new PdfPCell(new Phrase((differentParent[2].toString()),FontFactory.getFont(FontFactory.TIMES_ROMAN, 9)));
            cell.setBorderColor(BaseColor.GRAY)
            table.addCell(cell);
            cell = new PdfPCell(new Phrase((differentParent[3].toString()),FontFactory.getFont(FontFactory.TIMES_ROMAN, 9)));
            cell.setBorderColor(BaseColor.GRAY)
            table.addCell(cell);
        }
        document.add(table) //last partial table
    }

    def createNewPageForDifferentNamedParent(document) {
        PdfPTable table = new PdfPTable(4);
        table.setWidthPercentage(100);
        float[] columnWidths = [100f, 120f, 110f, 90f]
        table.setWidths(columnWidths);
        table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);

        document.newPage();
        writeDifferentNamedParentHeader(table);
        table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_LEFT);
        return table
    }

    public void writeDifferentNamedParentHeader(PdfPTable table) {
        // we add a cell with title
        Phrase title = new Phrase("Parents With Last Names Different From Students", FontFactory.getFont(FontFactory.TIMES_BOLD, 14));
        PdfPCell cell = new PdfPCell();
        cell.setColspan(4);
        cell.addElement(title);
        table.addCell(cell);
        // now we add a cell with parent title
        Phrase parentHeader = new Phrase("Parent", FontFactory.getFont(FontFactory.TIMES_ROMAN, 14));
        cell = new PdfPCell();
        cell.setColspan(2);
        cell.addElement(parentHeader);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(cell);
        // now we add a cell with student title
        Phrase studentHeader = new Phrase("Student", FontFactory.getFont(FontFactory.TIMES_ROMAN, 14));
        cell = new PdfPCell();
        cell.setColspan(2);
        cell.addElement(studentHeader);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(cell);
        // now "Last Name" and "First Name" headers
        Phrase lastNameTitle = new Phrase("Last Name", FontFactory.getFont(FontFactory.TIMES_BOLD, 10));
        Phrase firstNameTitle = new Phrase("First Name", FontFactory.getFont(FontFactory.TIMES_BOLD, 10));
        cell = new PdfPCell();
        cell.addElement(lastNameTitle);
        table.addCell(cell);
        cell = new PdfPCell();
        cell.addElement(firstNameTitle);
        table.addCell(cell);
        cell = new PdfPCell();
        cell.addElement(lastNameTitle);
        table.addCell(cell);
        cell = new PdfPCell();
        cell.addElement(firstNameTitle);
        table.addCell(cell);
    }

    def buildParentsWithDifferentLastNames(){
        def totalParentList = Parent.listOrderByLastName()
        def parentsWithDifferentLastNames = []
        totalParentList.each { parent->
            if (parent.lastName && parent.firstName) {
                def studentsInHouse = parent.getHousehold().getRegistration().getStudents()
                studentsInHouse.each { student ->
                    def currentStudentLastName = student.getLastName()
                    if (currentStudentLastName?.toLowerCase() != parent.lastName?.toLowerCase()) {
                        parentsWithDifferentLastNames.add([parent.lastName,parent.firstName,student.getLastName(),student.getFirstName()])
                    }
                }
            }
        }
        return parentsWithDifferentLastNames
    }
 }
