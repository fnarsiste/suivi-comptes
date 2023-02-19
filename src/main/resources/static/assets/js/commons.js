/**
Commons js file
*/

function hideSpinner() {
    $.MainApp.Hideloader();
}

function showSpinner(){
    $.MainApp.Showloader();
}

function encodeValue(value){
    return encodeURIComponent(value);
}

function showAnimatedError(errorDiv, message) {
    console.log({errorDiv, message});
}

function closeModal(id) {
    $('#' + id).modal('hide');
     $('.modal-backdrop').remove(); //fade show
}

function serializeObject(form){
    let o = {};
    let a = form.serializeArray();
    $.each(a, function() {
        if (o[this.name]) {
            if (!o[this.name].push) {
                o[this.name] = [o[this.name]];
            }
            o[this.name].push(this.value || '');
        } else {
            o[this.name] = this.value || '';
        }
    });
    return o;
}

function appDeleteListeRow(fn, e){
    e.preventDefault();
    const data = $(fn).data();
    swal({
        icon: "warning",
        title: "Etes vous sûr ?",
        text: "Une fois supprimée, vous ne serez plus en mesure de ...",
        buttons: true, dangerMode: true, closeOnEsc: false,
        closeOnClickOutside: false
    })
    .then((response) => {
        if(!response) return;
        showSpinner();
        const targetUrl = `${data.href}${data.id}/supprimer`;
        $.ajax({
            type : "POST",
            url : targetUrl,
            data : JSON.stringify(data),
            dataType: 'json',
            contentType: 'application/json',
            success : function(response) {
                hideSpinner();
                if(!response.result) return errorBox(response.message);
                defaultAlertAction(response.message, function() {
                    document.location.reload();
                });
            },
            error : function() {
                hideSpinner();
            }
        });
    });
}

function msgBox(message) {defaultAlertAction(message);}

function showSwallMessage(title, message, type, callback) {
    //"warning", "error", "success" and "info"
    console.log({title, message, type, callback});
    const $div = $('#js-temp-message');
    icon = type || 'info';
    title = title || 'Suivi Comptes';
    message = message || "<b>Aucun message n'est defini</b>";
    $div.html(message);
    const settings = {title, icon, text: message, content: $div, closeOnClickOutside: false};
    swal(settings).then((value) => {
        if(callback) callback();
    });
}

function defaultAlertAction(message, callback) {
    showSwallMessage(null,message, null, callback);
    return true;
}

function errorBox(message, callback) {
    showSwallMessage(null, message, 'error', callback);
    return true;
}

function getEltSelector(elt, defaultPrefix) {
	if (emptyValue(elt)) return '';
	else if (!isString(elt)) return elt;
	else {
		if (!elt.startsWith('.') && !elt.startsWith('#')) {
			defaultPrefix = !emptyValue(defaultPrefix) ? defaultPrefix : '#';
			return defaultPrefix+elt;
		} else return elt;
	}
}

function getAjaxResponse(response, defaultResp) {
	response = $.trim(response);
    return (!emptyValue(response)) ? JSON.parse(response) : defaultResp;
}

function emptyValue(value) {
	return (value == 'undefined')
		|| (value == undefined)
		|| (value == 'null')
		|| (value == null)
		|| (value == '');
}

function emptyValueAll(...values) {
	let res = true;
	for (var i=0; i<values.length; i++) {
		res = !emptyValue(values[i]);
		if (!res) break;
	}
	return res;
}

function emptyValueOne(...values) {
	let res = false;
	for (var i=0; i<values.length; i++) {
		res = emptyValue(values[i]);
        if (res) break;
	}
	return res;
}

function appendSubmitFormDataValue(fData, fDataStr, key, value) {
	if (emptyValue(fData)) 	 fData = new FormData();
	if (emptyValue(fDataStr)) fDataStr = '';
	if (isString(value)) value = encodeURIComponent(value);

	const fDataStrSeparator = '&';
	const fDataValSeparator = ',';
	const fDataValEqual = '=';

	if (fData.has(key)) {
		if (!emptyValue(value)) {
			let fDataArray = fDataStr.split(fDataStrSeparator);
			let fDataArrayValue;
			let fDataValuesArray;

			for (var i = 0; i < fDataArray.length; i++) {
				fDataArrayValue = fDataArray[i];
				if (fDataArrayValue.startsWith(key + fDataValEqual)) {
					fDataValuesArray = fDataArrayValue.split(fDataValEqual)[1].split(fDataValSeparator);
					if (!fDataValuesArray.includes(value)) fDataArray[i] += fDataValSeparator + value;
					break;
				}
			}
			fDataStr = fDataArray.join(fDataStrSeparator);
		}
	} else {
		if (fDataStr != '') fDataStr += fDataStrSeparator;
		fDataStr += key + '=' + value;
	}
	fData.append(key, value); //ajoute la clé même si elle existe déjà

	return {fData, fDataStr};
}

function doSubmitForm(form, event) {
	let $form = $(getEltSelector(form));
	let formData = new FormData($form[0]);
	let fData = new FormData();
	let fDataStr = '';
	let appendRes;

	// ajoute les champs du formulaire
	for (let entry of formData.entries()) {
		appendRes = appendSubmitFormDataValue(fData, fDataStr, entry[0], entry[1]);
		fData = appendRes['fData'];
		fDataStr = appendRes['fDataStr'];
	}

	// cas particulier : ajoute les champs des DataTable
	$form.find('table.dataTable').each(function () {
        let dtInstance = $(this).DataTable();
        let dtData = dtInstance.rows().data();

        dtData.each(function(data, index){
            let $inputs = $(dtInstance.row(index).node()).find('[name]').not(':disabled');

            $inputs.each(function () {
            	if ($(this).is(':checkbox') || $(this).is(':radio')) {
	            	if ($(this).is(':checked')) {
	            		appendRes = appendSubmitFormDataValue(fData, fDataStr, $(this).attr('name'), $(this).val());
						fData = appendRes['fData'];
						fDataStr = appendRes['fDataStr'];
	            	}
            	}
            	else {
            		appendRes = appendSubmitFormDataValue(fData, fDataStr, $(this).attr('name'), $(this).val());
					fData = appendRes['fData'];
					fDataStr = appendRes['fDataStr'];
            	}
            });
        });
	});

    showSpinner();
	let formAction = $form.attr('action');
	onSubmit(!emptyValue(formAction) ? formAction : location.href, fDataStr);

	event.preventDefault();
	return false;
}

function onSubmit(targetUrl, formData, options) {
	let defaultOptions = {
        onSuccess: null,
        onError: null,
        errorDiv: 'frm_error'
     };

    options = $.extend({}, defaultOptions, options);
	$.ajax({
        type : "POST",
        url : targetUrl,
        data : formData,
        // dataType : 'text', contentType: false,
        contentType: 'application/x-www-form-urlencoded;charset=ISO 8859-1',
        processData: false,
        success : function(response) {
            hideSpinner();
            response = $.trim(response);
            response = (response != '') ? JSON.parse(response) : {"result":"error", "type":"EmptyResponse", "message":"Aucune réponse"};
            if (response.result == 'error') {
                if ($(getEltSelector(options.errorDiv)).length > 0) {
                    showAnimatedError(options.errorDiv, response.message);
                } else {
                    defaultAlertAction(response.message);
                }
            } else {
                if (!emptyValue(options.onSuccess)) {
                    options.onSuccess(response);
                } else {
                    showOperationEndedMsg(response.msgType, response.endPage, {handle: response.handle, remaining: response.remaining});
                }
            }
        },
        error : function() {
            hideSpinner();
            if (!emptyValue(options.onError)) options.onError();
            else defaultAlertAction("<span style='color:red'>Une erreur est survenue lors de l'opération</span>");
        }
    });
}

function showOperationEndedMsg(msgType, endPage, options) {
    if(emptyValue(msgType)) return;
	let defaultOptions = {handle: "", remaining: ""};
    options = $.extend({}, defaultOptions, options);
    let msg = "Opération bien effectuée";
    switch (msgType) {
        case 'PROCESS_END':
            fermerPopUp('alert_box_traitement');
            alert_message_exercice_process_end("Traitement terminé", endPage);
            break;
        case 'EXERCICE':
            alert_message_exercice(msg, endPage);
            break;
        case 'LOADFILE':
            let msgRemaining = '';
            let msgHandle = '';
            let totalFile = parseInt(options.handle) + parseInt(options.remaining);

            msgHandle = '<div style="padding:10px"><li><p style="color:#0000FF;">Nombre de lignes traitées : ' + handle + ' / ' + totalFile + '</p></li>';

            if (parseInt(remaining) != 0) {
                msgRemaining = '<li><p style="color:#FF3300">Nombre de lignes non traitées : ' + remaining + ' / ' + totalFile + '</p></li></div>';
            } else msgRemaining = '</div>';
            alert_message_exercice('<h4"><b>' + msg + '</h4></b>.<br/>' + msgHandle + msgRemaining, endPage);
            break;
        default :
            switch (msgType) {
                case 'CREATE_SUCCES'			: msg = "Enregistrement bien effectué."; 				break;
                case 'UPDATE_SUCCES'			: msg = "Modification bien effectuée."; 					break;
                case 'DELETE_SUCCES'			: msg = "Suppression bien effectuée."; 					break;
                case 'FORWARD_SUCCES'			: msg = "Transmission bien effectuée."; 					break;
                case 'VALIDATION_SUCCES'		: msg = "Validation bien effectuée."; 					break;
                case 'VALIDATION_RESERVE_SUCCES': msg = "Validation sous réserve bien effectuée.";break;
                case 'RETOUR_SUCCES'			: msg = "Retour bien effectué."; 						break;
                case 'ANNULATION_SUCCES'		: msg = "Annulation bien effectuée."; 					break;
                case 'RECTIFICATION_SUCCES'		: msg = "Rectification bien effectuée."; 				break;
                case 'DIFFERER_SUCCES'			: msg = "Différé bien effectué."; 			break;
                case 'REJET_SUCCES'				: msg = "Rejet bien effectué."; 							break;
                case 'AFFECTATION_SUCCES'		: msg = "Affectation bien effectuée."; 					break;
                case 'RETRAIT_SUCCES'			: msg = "Retrait bien effectué."; 						break;
                case 'CONSOLIDATION_SUCCES'		: msg = "Consolidation bien effectuée."; 				break;
                case 'SUCCES'					: msg = "Opération bien effectuée"; 				break;
            }
            defaultAlertAction(msg, () => {if (endPage != '') document.location.href = endPage});
        break
    }
}

function appExit(fn, e) {
    e.preventDefault();
    const {contextpath} = $(fn).data();
    swal({
        title: "Suivi Compte!",
        text: `Voulez-vous réellement quitter l'application ?`,
        icon: "warning",
        buttons: ['Continuez', 'Quittez'],
        dangerMode: true,
    }).then((canExit) => {
        if (canExit) {
            document.location.href = contextpath;
        }
    });
}

function onChange(param, cible, format, prop_cible, onSuccess, onError) {
	console.log(param);
	var url = "find?ajax=true", type = "GET", data = null;
	if (param) url += "&" + param;
	doAjax(url, data, type, cible, format, prop_cible, onSuccess, onError);
	return false;
}

function doAjax(v_url, v_data, v_type, cible, format, prop_cible, onSuccess, onError) {
	if (v_url == null) errorBox("Veuillez pr&eacute;ciser l'url !");
	else if (v_type == null) errorBox("Veuillez pr&eacute;ciser le type d'envoi !");
	else if (cible == null) errorBox("Veuillez pr&eacute;ciser la cible d'affichage !");
	else if (format == null) errorBox("Veuillez pr&eacute;ciser le format !");
	else {
		showSpinner();
		$.ajax({
            type : v_type,
            url : v_url,
            data : v_data,
            success : function(response) {
                // TODO reloadSessionTimeoutListening();
                hideSpinner();
                // we have the response
                var cibleTab = cible.split(",");
                var formatTab = format.split(",");
                var prop_cibleTab = prop_cible.split(",");
                var responseTab = response.split(_FIND_PARTS_DELIMITER || "@@@"); // "_FIND_PARTS_DELIMITER" est defini dans "head.jsp"

                for (var i = 0; i < cibleTab.length; i++) {
                    var v_format = (formatTab.length > 1) ? formatTab[i] : format;
                    var v_cible = cibleTab[i];
                    var v_prop_cible = (prop_cibleTab.length > 1) ? prop_cibleTab[i] : prop_cible;
                    var v_response = $.trim(responseTab[i]);
                    var $cible = $('#' + v_cible);

                    if(v_cible=='v' || v_cible=='w' || v_cible=='y') $cible.css('display','block');

                    if (v_format == "text") $cible.val(v_response);
                    else if (v_format == "select") {
                        $cible.html('');
                        $cible.append(v_response);
                    } else if (v_format == "prop") $cible.attr(v_prop_cible, v_response);
                    else if (v_format == "redirect") window.location.replace(v_cible);
                    else if (v_format == "redirect-blank") window.open(v_cible, '_blank');
                    else{
                        if((v_cible=='v' || v_cible=='w' || v_cible=='y') && v_response.startsWith('{')){
                            $cible.css('display','none');
                        }
                        $cible.html(v_response);
                    }
                    if ($cible.is('select')) $cible.trigger('change.select2');
                }
                if (onSuccess) onSuccess(response);
            },
            error : function(jqXHR, exception) {
                //TODO reloadSessionTimeoutListening();
                if (jqXHR.status == 502) {
                    showSpinner();
                    setTimeout(() => location.reload(), 60000);
                } else {
                    hideSpinner();
                    var error_message = '';
                    if (jqXHR.status === 0) error_message = 'Pas connecté. Merci de vérifier le réseau.';
                    else if (jqXHR.status == 404) error_message = 'Page demandée introuvable. [404].';
                    else if (jqXHR.status == 500) error_message = 'Erreur interne au serveur [500].';
                    else if (exception === 'parsererror') error_message = 'Requête JSON parse failed.';
                    else if (exception === 'timeout') error_message = 'Erreur delai depassé.';
                    else if (exception === 'abort') error_message = 'Requête ajax demandée a été interrompue.';
                    else error_message = 'Erreur interne survenue : ' + jqXHR.responseText;
                    errorBox('Erreur rencontrée : ' + error_message);
                    if (onError) onError(jqXHR, exception);
                }
            }
        });
	}
}

$(document).ready(function(){
   // Initalize data table with buttons
   const table = $('#datatable-buttons').DataTable({
      lengthChange: !true,
      buttons: ['copy', 'excel', 'pdf'], //, 'colvis'],
      language: LANG,
      order: [[TABLE_SORTING, 'asc']]
   });
   table.buttons().container().appendTo('#datatable-buttons_wrapper .col-md-6:eq(0)');

   $('.js-button-logout').on('click', function(e) {appExit(this, e);});

    // Tout formulaire d'enregistrement et de modification de données
   $('body').on('submit', '#form, #'+$(document.myform).attr('id'), function (e) {doSubmitForm(this, e);});

   // Ecoute sur les boutons de suppression sur les listes de données
   $('.js-app-button-delete').on('click', function (e) {appDeleteListeRow(this, e)});
    // Convertir en maj les champs tagués de 'js-to-upper'
   $('.js-to-upper').on('change', function (){$(this).val($(this).val().toUpperCase());});

   $('.select2').select2({width: '100%'});

   $('.js-tooltip').each(function(i) {$(this).tooltip({placement: 'bottom'});})

   // Toutes les pages qui aurient définies cette fonction
    if(typeof runOnceAllLoaded === 'function') runOnceAllLoaded();
});